package com.anjunar.sql.builder.joins.jpa;

import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.JPAHelper;
import com.anjunar.sql.builder.joins.Join;
import com.google.common.base.Strings;

public class OneToManyJoin<U, E> extends Join<U, E> {

    private final String mappedBy;

    public OneToManyJoin(Class<E> result, Type type, BeanProperty<?, ?> property, String mappedBy) {
        super(result, type, property);
        this.mappedBy = mappedBy;
    }

    private String joinTableName() {
        return JPAHelper.joinTableName(getBeanProperty(), getParent(), this);
    }

    private String joinTableAlias() {
        return joinTableName().toLowerCase();
    }

    private String joinOnExpression() {
        return getParent().destinationTableAlias() +
                "." +
                JPAHelper.id(getParent().getSource()) +
                " = " +
                joinTableAlias() +
                "." +
                JPAHelper.joinTableColumnName(getBeanProperty(), getParent());
    }

    private String destinationOnExpression() {
        return destinationTableAlias() +
                "." +
                JPAHelper.id(getSource()) +
                " = " +
                joinTableAlias() +
                "." +
                JPAHelper.inverseJoinColumn(getBeanProperty(), this);
    }

    public String execute(Context context) {
        if (Strings.isNullOrEmpty(mappedBy)) {
            return joinTableName() +
                    " " +
                    joinTableAlias() +
                    " on " +
                    joinOnExpression() +
                    " join " +
                    destinationTableName() +
                    " " +
                    destinationTableAlias() +
                    " on " +
                    destinationOnExpression();
        } else {
            throw new RuntimeException("not implemented yet");
        }
    }

}

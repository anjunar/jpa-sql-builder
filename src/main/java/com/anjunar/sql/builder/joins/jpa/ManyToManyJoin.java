package com.anjunar.sql.builder.joins.jpa;

import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.JPAHelper;
import com.anjunar.sql.builder.joins.Join;
import com.google.common.base.Strings;

public class ManyToManyJoin<U, E> extends Join<U, E> {

    public ManyToManyJoin(Class<E> result, Type type, BeanProperty<?, ?> property) {
        super(result, type, property);
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
    }

}

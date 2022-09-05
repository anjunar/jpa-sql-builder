package com.anjunar.sql.builder.joins.jpa.mapped;

import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.JPAHelper;
import com.anjunar.sql.builder.joins.Join;

public class ManyToManyMappedJoin<U, E> extends Join<U, E> {

    private final BeanProperty<?, ?> sourceProperty;

    public ManyToManyMappedJoin(Class<E> result, Type type, BeanProperty<?, ?> property, BeanProperty<?,?> sourceProperty) {
        super(result, type, property);
        this.sourceProperty = sourceProperty;
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
                JPAHelper.inverseJoinColumn(getBeanProperty(), this);
    }

    private String destinationOnExpression() {
        return destinationTableAlias() +
                "." +
                JPAHelper.id(getSource()) +
                " = " +
                joinTableAlias() +
                "." +
                JPAHelper.joinTableColumnNameMapped(sourceProperty, this);
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

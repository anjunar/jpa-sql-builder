package com.anjunar.sql.builder.joins.jpa;

import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.JPAHelper;
import com.anjunar.sql.builder.joins.Join;

public class ManyToOneJoin<U,E> extends Join<U,E> {

    public ManyToOneJoin(Class<E> result, Type type, BeanProperty<?, ?> property) {
        super(result, type, property);
    }

    private String destinationOnExpression() {
        return destinationTableAlias() +
                "." +
                JPAHelper.id(getSource()) +
                " = " +
                getParent().destinationTableAlias() +
                "." +
                JPAHelper.joinColumn(getBeanProperty(), this);
    }

    @Override
    public String execute(Context context) {
        return destinationTableName() +
                " " +
                destinationTableAlias() +
                " on " +
                destinationOnExpression();
    }
}

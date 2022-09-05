package com.anjunar.sql.builder.joins.jpa.mapped;

import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.JPAHelper;
import com.anjunar.sql.builder.joins.Join;

public class OneToOneMappedJoin<U,E> extends Join<U,E> {

    public OneToOneMappedJoin(Class<E> result, Type type, BeanProperty<?, ?> property) {
        super(result, type, property);
    }

    private String destinationOnExpression() {
        return getParent().destinationTableAlias() +
                "." +
                JPAHelper.id(getSource()) +
                " = " +
                destinationTableAlias() +
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

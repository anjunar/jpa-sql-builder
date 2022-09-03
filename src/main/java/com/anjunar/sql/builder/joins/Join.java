package com.anjunar.sql.builder.joins;

import com.anjunar.sql.builder.AbstractJoin;

public class Join<U, E> extends AbstractJoin<U, E> {

    public enum Type {
        LEFT,
        RIGHT,
        INNER,
        NATURAL,
        FULL
    }

    private final Type type;

    public Join(Class<E> result, Type type) {
        super(result);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

}
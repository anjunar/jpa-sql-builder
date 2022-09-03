package com.anjunar.sql.builder.joins;

import com.anjunar.sql.builder.Join;

public class NormalJoin<U, E> extends Join<U, E> {

    public enum Type {
        LEFT,
        RIGHT,
        INNER,
        NATURAL, FULL
    }

    private final Type type;

    public NormalJoin(Class<E> result, Type type) {
        super(result);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

}

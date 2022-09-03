package com.anjunar.sql.builder.functions.postgres;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.joins.postgres.JsonJoin;

public class JsonEqualFunction<E, U> extends AbstractFunction<E> {
    private final JsonJoin<E, U> join;
    private final String property;
    private final String value;

    public JsonEqualFunction(JsonJoin<E, U> join, String property, String value) {
        this.join = join;
        this.property = property;
        this.value = value;
    }

    public JsonJoin<E, U> getJoin() {
        return join;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(join.getProperty())
                .append(" ->> ")
                .append("'")
                .append(getProperty())
                .append("'")
                .append(" = ")
                .append("'")
                .append(getValue())
                .append("'")
                .toString();
    }
}

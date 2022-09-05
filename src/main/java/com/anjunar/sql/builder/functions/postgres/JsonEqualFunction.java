package com.anjunar.sql.builder.functions.postgres;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;
import com.anjunar.sql.builder.joins.postgres.JsonJoin;

public class JsonEqualFunction<E, U> extends AbstractFunction<E> {
    private final JsonJoin<E, U> join;
    private final String property;
    private final Expression<String> value;

    public JsonEqualFunction(JsonJoin<E, U> join, String property, Expression<String> value) {
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

    public Expression<String> getValue() {
        return value;
    }

    @Override
    public String execute(Context context) {
        return join.getProperty() +
                " ->> " +
                "'" +
                getProperty() +
                "'" +
                " = " +
                getValue().execute(context);
    }
}

package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Predicate;
import com.anjunar.sql.builder.joins.JsonJoin;

public class JsonEqualPredicate<E, U> extends Predicate {
    private final JsonJoin<E, U> join;
    private final String property;
    private final String value;

    public JsonEqualPredicate(JsonJoin<E, U> join, String property, String value) {
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

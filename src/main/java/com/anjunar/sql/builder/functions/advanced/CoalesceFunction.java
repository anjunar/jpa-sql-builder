package com.anjunar.sql.builder.functions.advanced;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class CoalesceFunction<E> extends AbstractFunction<E> {

    private final Object value;

    private final Expression<E> path;

    public CoalesceFunction(Object value, Expression<E> path) {
        this.value = value;
        this.path = path;
    }

    public Expression<E> getPath() {
        return path;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        context.mappings().put(next, value);

        return new StringBuilder()
                .append("coalesce(")
                .append(getPath().execute(context))
                .append(", :")
                .append(next)
                .append(")")
                .toString();
    }
}

package com.anjunar.sql.builder.functions.advanced;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class IsNullFunction<E> extends AbstractFunction<E> {

    private final Expression<E> path;

    public IsNullFunction(Expression<E> path) {
        this.path = path;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(path.execute(context))
                .append(" is null")
                .toString();
    }
}

package com.anjunar.sql.builder.functions.advanced;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class IsNotNullFunction<E> extends AbstractFunction<E> {

    private final Expression<E> path;

    public IsNotNullFunction(Expression<E> path) {
        this.path = path;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(path.execute(context))
                .append(" is not null")
                .toString();
    }
}

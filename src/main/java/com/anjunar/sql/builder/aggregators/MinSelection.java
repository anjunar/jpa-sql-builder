package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class MinSelection<E, X> extends AbstractPathSelection<E,X> {

    public MinSelection(Expression<X> path) {
        super(path);
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("min(")
                .append(getPath().execute(context))
                .append(") ")
                .toString();
    }
}

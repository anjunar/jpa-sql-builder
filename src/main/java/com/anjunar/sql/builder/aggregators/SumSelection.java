package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class SumSelection<E, X> extends AbstractPathSelection<E, X> {

    public SumSelection(Expression<X> path) {
        super(path);
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("sum(")
                .append(getPath().execute(context))
                .append(")")
                .toString();
    }
}

package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class AvgSelection<E, X> extends AbstractPathSelection<E, X> {

    public AvgSelection(Expression<X> path) {
        super(path);
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("avg(")
                .append(getPath().execute(context))
                .append(")")
                .toString();
    }
}

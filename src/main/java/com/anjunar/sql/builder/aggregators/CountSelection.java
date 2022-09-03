package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class CountSelection<E, X> extends AbstractPathSelection<E, X> {

    public CountSelection(Expression path) {
        super(path);
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("count(")
                .append(getPath().execute(context))
                .append(")")
                .toString();
    }
}

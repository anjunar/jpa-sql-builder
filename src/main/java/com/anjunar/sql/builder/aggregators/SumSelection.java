package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Path;

public class SumSelection<E, X> extends PathSelection<E, X> {

    public SumSelection(Path<X> path) {
        super(path);
    }

    @Override
    public String execute() {
        return new StringBuilder()
                .append("sum(")
                .append(getPath().execute())
                .append(") ")
                .toString();
    }
}

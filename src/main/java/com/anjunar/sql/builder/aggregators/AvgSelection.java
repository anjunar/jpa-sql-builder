package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Path;

public class AvgSelection<E, X> extends PathSelection<E, X> {

    public AvgSelection(Path<X> path) {
        super(path);
    }

    @Override
    public String execute() {
        return new StringBuilder()
                .append("avg(")
                .append(getPath().execute())
                .append(") ")
                .toString();
    }
}

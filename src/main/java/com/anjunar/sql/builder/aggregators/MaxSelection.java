package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Path;

public class MaxSelection<E, X> extends PathSelection<E, X> {

    public MaxSelection(Path<X> path) {
        super(path);
    }

    @Override
    public String execute() {
        return new StringBuilder()
                .append("max(")
                .append(getPath().execute())
                .append(") ")
                .toString();
    }
}

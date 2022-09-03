package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Path;

public class MinSelection<E, X> extends PathSelection<E,X> {

    public MinSelection(Path<X> path) {
        super(path);
    }

    @Override
    public String execute() {
        return new StringBuilder()
                .append("min(")
                .append(getPath().execute())
                .append(") ")
                .toString();
    }
}

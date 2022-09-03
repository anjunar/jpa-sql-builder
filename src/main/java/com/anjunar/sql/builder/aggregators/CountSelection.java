package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.From;
import com.anjunar.sql.builder.Path;
import com.anjunar.sql.builder.Selection;

public class CountSelection<E, X> extends PathSelection<E, X> {

    public CountSelection(Path path) {
        super(path);
    }

    @Override
    public String execute() {
        return new StringBuilder()
                .append("count(")
                .append(getPath().execute())
                .append(") ")
                .toString();
    }
}

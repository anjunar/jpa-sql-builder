package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.From;
import com.anjunar.sql.builder.Selection;

public class CountSelection<E, X> extends Selection<E> {
    private final From<X> from;

    public CountSelection(From<X> from) {
        super();
        this.from = from;
    }

    public From<X> getFrom() {
        return from;
    }

    @Override
    public String execute() {
        return new StringBuilder()
                .append("count(")
                .append(from.execute())
                .append(") ")
                .toString();
    }
}

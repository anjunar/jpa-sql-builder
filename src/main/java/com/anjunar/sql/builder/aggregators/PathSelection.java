package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Path;
import com.anjunar.sql.builder.Selection;

public abstract class PathSelection<E, X> extends Selection<E> {

    private final Path<X> path;

    public PathSelection(Path<X> path) {
        this.path = path;
    }

    public Path<X> getPath() {
        return path;
    }
}

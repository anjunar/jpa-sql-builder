package com.anjunar.sql.builder.aggregators;

import com.anjunar.sql.builder.Expression;
import com.anjunar.sql.builder.Path;
import com.anjunar.sql.builder.AbstractSelection;

public abstract class AbstractPathSelection<E, X> extends AbstractSelection<E> {

    private final Expression<X> path;

    public AbstractPathSelection(Expression<X> path) {
        this.path = path;
    }

    public Path<X> getPath() {
        return (Path<X>) path;
    }
}

package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;
import com.anjunar.sql.builder.AbstractPredicate;

public class BetweenPredicate<E> extends AbstractPredicate<E> {
    private final Expression<E> path;
    private final Expression<Comparable<?>> from;
    private final Expression<Comparable<?>> to;

    public BetweenPredicate(Expression<E> path, Expression<Comparable<?>> from, Expression<Comparable<?>> to) {
        this.path = path;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(path.execute(context))
                .append(" between ")
                .append(from.execute(context))
                .append(" and ")
                .append(from.execute(context))
                .toString();
    }
}

package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;
import com.anjunar.sql.builder.AbstractPredicate;

public class BetweenPredicate<E> extends AbstractPredicate<E> {
    private final Expression<E> path;
    private final Comparable<?> from;
    private final Comparable<?> to;

    public BetweenPredicate(Expression<E> path, Comparable<?> from, Comparable<?> to) {
        this.path = path;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(Context context) {
        Integer toIndex = context.next();
        context.mappings().put(toIndex, from);

        Integer fromIndex = context.next();
        context.mappings().put(fromIndex, from);

        return new StringBuilder()
                .append(path.execute(context))
                .append(" between ")
                .append(":")
                .append(toIndex)
                .append(" and ")
                .append(":")
                .append(fromIndex)
                .toString();
    }
}

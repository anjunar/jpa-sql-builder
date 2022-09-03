package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Path;
import com.anjunar.sql.builder.Predicate;

public class BetweenPredicate<E> extends Predicate {
    private final Path<E> path;
    private final Comparable<?> from;
    private final Comparable<?> to;

    public BetweenPredicate(Path<E> path, Comparable<?> from, Comparable<?> to) {
        this.path = path;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(Context context) {
        Integer toIndex = context.next();
        mapping().put(toIndex, from);

        Integer fromIndex = context.next();
        mapping().put(fromIndex, from);

        context.mappings().putAll(mapping());

        return new StringBuilder()
                .append(path.execute())
                .append(" between ")
                .append(":")
                .append(toIndex)
                .append(" and ")
                .append(":")
                .append(fromIndex)
                .toString();
    }
}

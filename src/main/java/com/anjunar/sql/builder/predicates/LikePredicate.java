package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Path;
import com.anjunar.sql.builder.Predicate;

public class LikePredicate<E> extends Predicate {

    private final String value;

    private final Path<E> path;

    public LikePredicate(String value, Path<E> path) {
        this.value = value;
        this.path = path;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        mapping().put(next, value);

        context.mappings().putAll(mapping());

        return new StringBuilder()
                .append(path.execute())
                .append(" ")
                .append("like ")
                .append(":")
                .append(next)
                .toString();
    }

    public String getValue() {
        return value;
    }

    public Path<E> getPath() {
        return path;
    }
}

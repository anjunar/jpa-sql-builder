package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Path;
import com.anjunar.sql.builder.Predicate;

public class IsNotNullPredicate<E> extends Predicate {

    private final Path<E> path;

    public IsNotNullPredicate(Path<E> path) {
        this.path = path;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(path.execute())
                .append(" is not null")
                .toString();
    }
}

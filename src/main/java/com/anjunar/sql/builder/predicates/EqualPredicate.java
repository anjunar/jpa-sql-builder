package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Path;
import com.anjunar.sql.builder.Predicate;

public class EqualPredicate<E, U> extends Predicate {
    private final Path<E> lhs;
    private final Path<U> rhs;

    public EqualPredicate(Path<E> lhs, Path<U> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(lhs.execute())
                .append(" = ")
                .append(rhs.execute())
                .toString();
    }
}

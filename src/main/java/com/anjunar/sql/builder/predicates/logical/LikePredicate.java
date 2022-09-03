package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;
import com.anjunar.sql.builder.AbstractPredicate;

public class LikePredicate<E> extends AbstractPredicate<E> {

    private final String value;

    private final Expression<E> path;

    public LikePredicate(String value, Expression<E> path) {
        this.value = value;
        this.path = path;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        context.mappings().put(next, value);

        return new StringBuilder()
                .append(path.execute(context))
                .append(" ")
                .append("like ")
                .append(":")
                .append(next)
                .toString();
    }

    public String getValue() {
        return value;
    }

    public Expression<E> getPath() {
        return path;
    }
}

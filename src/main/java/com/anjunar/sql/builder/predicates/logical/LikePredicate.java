package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;
import com.anjunar.sql.builder.AbstractPredicate;

public class LikePredicate<E> extends AbstractPredicate<E> {

    private final Expression<String> value;

    private final Expression<E> path;

    public LikePredicate(Expression<String> value, Expression<E> path) {
        this.value = value;
        this.path = path;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(path.execute(context))
                .append(" ")
                .append("like ")
                .append(value.execute(context))
                .toString();
    }

    public Expression<E> getPath() {
        return path;
    }
}

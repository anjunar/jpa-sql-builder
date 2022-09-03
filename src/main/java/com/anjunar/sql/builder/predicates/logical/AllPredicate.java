package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.*;

public class AllPredicate<E, U> extends AbstractPredicate<E> {

    private final Expression<E> path;

    private final Query<U> query;

    public AllPredicate(Expression<E> path, Query<U> query) {
        this.path = path;
        this.query = query;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(path.execute(context))
                .append(" = all (")
                .append(query.execute(context))
                .append(")")
                .toString();
    }
}

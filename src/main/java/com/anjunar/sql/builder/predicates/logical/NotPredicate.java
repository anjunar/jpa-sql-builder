package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.AbstractPredicate;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class NotPredicate extends AbstractPredicate<Comparable<?>> {

    private final Expression<? extends Comparable<?>> path;

    public NotPredicate(Expression<? extends Comparable<?>> path) {
        this.path = path;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(path.execute(context))
                .append(" not")
                .toString();
    }

}

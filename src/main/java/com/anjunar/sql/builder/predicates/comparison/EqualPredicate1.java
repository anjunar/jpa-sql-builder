package com.anjunar.sql.builder.predicates.comparison;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;
import com.anjunar.sql.builder.AbstractPredicate;

public class EqualPredicate1<E, U> extends AbstractPredicate<E> {
    private final Expression<E> lhs;
    private final Expression<U> rhs;

    public EqualPredicate1(Expression<E> lhs, Expression<U> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(lhs.execute(context))
                .append(" = ")
                .append(rhs.execute(context))
                .toString();
    }
}

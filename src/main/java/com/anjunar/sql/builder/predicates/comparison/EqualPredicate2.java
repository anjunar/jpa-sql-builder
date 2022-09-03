package com.anjunar.sql.builder.predicates.comparison;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;
import com.anjunar.sql.builder.AbstractPredicate;

public class EqualPredicate2<E, U> extends AbstractPredicate<E> {
    private final Expression<E> lhs;
    private final Object value;

    public EqualPredicate2(Expression<E> lhs, Object value) {
        this.lhs = lhs;
        this.value = value;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        context.mappings().put(next, value);

        return new StringBuilder()
                .append(lhs.execute(context))
                .append(" = :")
                .append(next)
                .toString();
    }
}

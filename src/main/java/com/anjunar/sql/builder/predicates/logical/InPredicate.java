package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;
import com.anjunar.sql.builder.AbstractPredicate;

import java.util.List;

public class InPredicate<E> extends AbstractPredicate<E> {

    private final Expression<E> path;
    private final List<String> values;

    public InPredicate(Expression<E> path, List<String> values) {
        this.path = path;
        this.values = values.stream().map(value -> "'" + value + "'").toList();
    }

    public Expression<E> getPath() {
        return path;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        context.mappings().put(next, values);

        return new StringBuilder()
                .append(getPath().execute(context))
                .append(" in")
                .append(" ( :")
                .append(next)
                .append(")")
                .toString();
    }
}

package com.anjunar.sql.builder.functions.postgres;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class LevenstheinFunction<E> extends AbstractFunction<E> {

    private final String value;
    private final Expression<E> attribute;

    public LevenstheinFunction(String value, Expression<E> attribute) {
        this.value = value;
        this.attribute = attribute;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        context.mappings().put(next, value);

        return new StringBuilder()
                .append("levensthein(")
                .append(attribute.execute(context))
                .append(", :")
                .append(next)
                .append(")")
                .toString();
    }
}

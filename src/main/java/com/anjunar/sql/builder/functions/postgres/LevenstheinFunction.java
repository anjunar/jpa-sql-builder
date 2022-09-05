package com.anjunar.sql.builder.functions.postgres;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class LevenstheinFunction extends AbstractFunction<String> {

    private final Expression<String> value;
    private final Expression<String> attribute;

    public LevenstheinFunction(Expression<String> value, Expression<String> attribute) {
        this.value = value;
        this.attribute = attribute;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("levensthein(")
                .append(attribute.execute(context))
                .append(", ")
                .append(value.execute(context))
                .append(")")
                .toString();
    }
}

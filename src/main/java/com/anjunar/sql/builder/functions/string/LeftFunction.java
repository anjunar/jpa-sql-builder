package com.anjunar.sql.builder.functions.string;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class LeftFunction extends AbstractFunction<String> {

    private final Expression<String> value;

    private final Expression<Integer> chars;

    public LeftFunction(Expression<String> value, Expression<Integer> chars) {
        this.value = value;
        this.chars = chars;
    }

    @Override
    public String execute(Context context) {
        return "left(" +
                value.execute(context) +
                ", " +
                chars.execute(context) +
                ")";
    }
}

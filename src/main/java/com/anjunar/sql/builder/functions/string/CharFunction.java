package com.anjunar.sql.builder.functions.string;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class CharFunction extends AbstractFunction<String> {

    private final Expression<Integer> value;

    public CharFunction(Expression<Integer> value) {
        this.value = value;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("char(")
                .append(value.execute(context))
                .append(")")
                .toString();
    }
}

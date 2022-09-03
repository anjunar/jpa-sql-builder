package com.anjunar.sql.builder.functions.string;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;

public class CharFunction extends AbstractFunction<String> {

    private final Integer value;

    public CharFunction(Integer value) {
        this.value = value;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        context.mappings().put(next, value);

        return new StringBuilder()
                .append("char(:")
                .append(next)
                .append(")")
                .toString();
    }
}

package com.anjunar.sql.builder.functions.string;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

import java.util.List;

public class ConcatFunction extends AbstractFunction<String> {

    private final List<Expression<String>> values;

    public ConcatFunction(List<Expression<String>> values) {
        this.values = values;
    }

    @Override
    public String execute(Context context) {
        return String.join(" + ", values.stream().map(value -> value.execute(context)).toList());
    }
}

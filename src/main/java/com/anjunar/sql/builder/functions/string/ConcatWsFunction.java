package com.anjunar.sql.builder.functions.string;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

import java.util.List;

public class ConcatWsFunction extends AbstractFunction<String> {

    private final String seperator;
    private final List<Expression<String>> values;

    public ConcatWsFunction(String seperator, List<Expression<String>> values) {
        this.seperator = seperator;
        this.values = values;
    }

    @Override
    public String execute(Context context) {
        return "concat_ws('" + seperator + "', " +  String.join(", ", values.stream().map(value -> value.execute(context)).toList()) + ")";
    }

}

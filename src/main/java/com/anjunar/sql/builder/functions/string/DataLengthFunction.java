package com.anjunar.sql.builder.functions.string;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class DataLengthFunction extends AbstractFunction<String> {

    private final Expression<String> expression;

    public DataLengthFunction(Expression<String> expression) {
        this.expression = expression;
    }

    @Override
    public String execute(Context context) {
        return "dataLength(" + expression.execute(context) + ")";
    }
}

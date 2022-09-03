package com.anjunar.sql.builder.functions.string;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class ASCIIFunction extends AbstractFunction<String> {

    private final Expression<String> path;

    public ASCIIFunction(Expression<String> path) {
        this.path = path;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("ASCII(")
                .append(path.execute(context))
                .append(")")
                .toString();
    }
}

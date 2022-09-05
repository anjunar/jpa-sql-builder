package com.anjunar.sql.builder.functions.string;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class DifferenceFunction extends AbstractFunction<String> {

    private final Expression<String> lhs;

    private final Expression<String> rhs;

    public DifferenceFunction(Expression<String> lhs, Expression<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String execute(Context context) {
        return "difference(" +
                lhs.execute(context) +
                ", " +
                rhs.execute(context) +
                ")";
    }
}

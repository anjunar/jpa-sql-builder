package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.AbstractPredicate;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class ArithmeticPredicate extends AbstractPredicate<Number> {

    public enum Type {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        MODULO("%");

        private final String operator;

        Type(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }
    }

    private final Expression<? extends Number> lhs;
    private final Expression<? extends Number> rhs;

    private final Type type;

    public ArithmeticPredicate(Expression<? extends Number> lhs, Expression<? extends Number> rhs, Type type) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.type = type;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append(lhs.execute(context))
                .append(" ")
                .append(type.getOperator())
                .append(" ")
                .append(rhs.execute(context))
                .toString();
    }
}

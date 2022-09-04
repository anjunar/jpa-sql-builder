package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.AbstractPredicate;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class CompoundPredicate extends AbstractPredicate<Number> {

    public enum Type {
        ADD_EQUALS("+="),
        SUBTRACT_EQUALS("-="),
        MULTIPLY_EQUALS("*="),
        DIVIDE_EQUALS("/="),
        MODULO_EQUALS("%="),
        BITWISE_AND_EQUALS("&="),
        BITWISE_EXCLUSIVE_OR_EQUALS("^="),
        BITWISE_OR_EQUALS("|=");

        private final String operator;

        Type(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }
    }

    private final Expression<? extends Comparable<?>> lhs;
    private final Expression<? extends Comparable<?>> rhs;

    private final Type type;

    public CompoundPredicate(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs, Type type) {
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

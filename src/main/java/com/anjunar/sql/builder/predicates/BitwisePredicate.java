package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.AbstractPredicate;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class BitwisePredicate extends AbstractPredicate<Number> {

    public enum Type {
        BITWISE_AND("&"),
        BITWISE_OR("|"),
        BITWISE_EXCLUSIVE_OR("^");

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

    public BitwisePredicate(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs, Type type) {
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

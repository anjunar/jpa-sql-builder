package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.AbstractPredicate;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class ComparisonPredicate<E,U> extends AbstractPredicate<E> {

    public enum Type {
        EQUAL("="),
        GREATER_THAN(">"),
        LESS_THAN("<"),
        GREATER_THAN_OR_EQUAL(">="),
        LESS_THAN_OR_EQUAL("<="),
        NOT_EQUAL_TO("<>");

        final String operator;

        Type(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }
    }

    private final Expression<E> lhs;
    private final Expression<U> rhs;
    private final Type type;

    public ComparisonPredicate(Expression<E> lhs, Expression<U> rhs, Type type) {
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

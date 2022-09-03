package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.AbstractPredicate;
import com.anjunar.sql.builder.Expression;

import java.util.Arrays;
import java.util.List;

public class AndPredicate extends AbstractPredicate<Boolean> {

    private final List<Expression<?>> predicates;

    public AndPredicate(Expression<?>... predicates) {
        this.predicates = Arrays.asList(predicates);
    }

    @Override
    public String execute(Context context) {
        return String.join(" and ", predicates.stream().map(predicate -> predicate.execute(context)).toList());
    }

}

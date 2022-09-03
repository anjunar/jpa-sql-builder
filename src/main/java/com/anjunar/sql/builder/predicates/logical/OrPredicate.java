package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.AbstractPredicate;
import com.anjunar.sql.builder.Expression;

import java.util.Arrays;
import java.util.List;

public class OrPredicate extends AbstractPredicate<Boolean> {

    private final List<Expression<?>> predicates;

    public OrPredicate(Expression<?>... predicates) {
        this.predicates = Arrays.asList(predicates);
    }

    @Override
    public String execute(Context context) {
        return String.join(" or ", predicates.stream().map(predicate -> predicate.execute(context)).toList());
    }
}

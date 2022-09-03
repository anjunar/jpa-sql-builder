package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Predicate;

import java.util.Arrays;
import java.util.List;

public class OrPredicate extends Predicate {

    private final List<Predicate> predicates;

    public OrPredicate(Predicate... predicates) {
        this.predicates = Arrays.asList(predicates);
    }

    @Override
    public String execute(Context context) {
        return String.join(" or ", predicates.stream().map(predicate -> predicate.execute(context)).toList());
    }
}

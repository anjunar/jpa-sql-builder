package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Predicate;
import com.anjunar.sql.builder.Query;

public class ExistPredicate<E> extends Predicate {
    private final Query<E> select;

    public ExistPredicate(Query<E> select) {
        this.select = select;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("exists (")
                .append(select.execute())
                .append(")")
                .toString();

    }
}

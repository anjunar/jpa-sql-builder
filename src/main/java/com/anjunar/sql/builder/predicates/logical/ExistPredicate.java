package com.anjunar.sql.builder.predicates.logical;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.AbstractPredicate;
import com.anjunar.sql.builder.Query;

public class ExistPredicate<E> extends AbstractPredicate<E> {
    private final Query<E> select;

    public ExistPredicate(Query<E> select) {
        this.select = select;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("exists (")
                .append(select.execute(context))
                .append(")")
                .toString();

    }
}

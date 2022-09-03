package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Predicate;
import com.anjunar.sql.builder.Selection;

public class GreaterThanPredicate<E extends Comparable<?>> extends Predicate {
    private final Selection<E> selection;
    private final Comparable<?> value;

    public GreaterThanPredicate(Selection<E> selection, Comparable<?> value) {
        this.selection = selection;
        this.value = value;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        mapping().put(next, value);

        context.mappings().putAll(mapping());

        return new StringBuilder()
                .append(selection.execute())
                .append("> :")
                .append(next)
                .toString();

    }
}

package com.anjunar.sql.builder.predicates.comparison;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.AbstractPredicate;
import com.anjunar.sql.builder.AbstractSelection;

public class GreaterThanPredicate<E extends Comparable<?>> extends AbstractPredicate<E> {
    private final AbstractSelection<E> selection;
    private final Comparable<?> value;

    public GreaterThanPredicate(AbstractSelection<E> selection, Comparable<?> value) {
        this.selection = selection;
        this.value = value;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        context.mappings().put(next, value);

        return new StringBuilder()
                .append(selection.execute(context))
                .append("> :")
                .append(next)
                .toString();

    }
}

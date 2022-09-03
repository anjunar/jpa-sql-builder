package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Path;
import com.anjunar.sql.builder.Predicate;

public class LevenstheinPredicate<E> extends Predicate {

    private final String value;
    private final Path<E> attribute;

    public LevenstheinPredicate(String value, Path<E> attribute) {
        this.value = value;
        this.attribute = attribute;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        mapping().put(next, value);

        context.mappings().putAll(mapping());

        return new StringBuilder()
                .append("levensthein(")
                .append(attribute.execute())
                .append(", :")
                .append(next)
                .append(")")
                .toString();
    }
}

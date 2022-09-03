package com.anjunar.sql.builder.predicates;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Path;
import com.anjunar.sql.builder.Predicate;

import java.util.List;

public class InPredicate<E> extends Predicate {

    private final Path<E> path;
    private final List<String> values;

    public InPredicate(Path<E> path, List<String> values) {
        this.path = path;
        this.values = values.stream().map(value -> "'" + value + "'").toList();
    }

    public Path<E> getPath() {
        return path;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        mapping().put(next, values);

        context.mappings().putAll(mapping());

        return new StringBuilder()
                .append(getPath().execute())
                .append(" in")
                .append(" ( :")
                .append(next)
                .append(")")
                .toString();
    }
}

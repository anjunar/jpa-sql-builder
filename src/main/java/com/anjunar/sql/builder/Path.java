package com.anjunar.sql.builder;

import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.predicates.InPredicate;
import jakarta.persistence.Column;

import java.util.Arrays;

public class Path<E> extends Selection<E> {
    private final From<E> parent;

    private final String columnName;

    public Path(String attribute, From<E> parent) {
        this.parent = parent;

        BeanModel<E> beanModel = BeanIntrospector.create(parent.getSource());
        BeanProperty<E, ?> property = beanModel.get(attribute);
        Column column = property.getAnnotation(Column.class);
        if (column == null) {
            columnName = attribute;
        } else {
            columnName = column.name();
        }
    }

    public From<E> getParent() {
        return parent;
    }

    public String execute() {
        return new StringBuilder()
                .append(parent.getAlias())
                .append(".")
                .append(columnName)
                .toString();
    }

    public InPredicate in(String... values) {
        return new InPredicate(this, Arrays.asList(values));
    }
}
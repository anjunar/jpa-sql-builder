package com.anjunar.sql.builder;

import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.predicates.logical.InPredicate;
import jakarta.persistence.Column;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Arrays;

public class Path<E> extends AbstractSelection<E> {
    private final From<E> parent;

    private final String columnName;

    public <U> Path(SingularAttribute<U, E> attribute, From<E> parent) {
        this.parent = parent;

        BeanModel<E> beanModel = BeanIntrospector.create(parent.getSource());
        BeanProperty<E, ?> property = beanModel.get(attribute.getName());
        Column column = property.getAnnotation(Column.class);
        if (column == null) {
            columnName = attribute.getName();
        } else {
            columnName = column.name();
        }
    }

    public From<E> getParent() {
        return parent;
    }

    public String execute(Context context) {
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

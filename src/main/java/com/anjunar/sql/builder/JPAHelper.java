package com.anjunar.sql.builder;

import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.joins.Join;
import jakarta.persistence.*;

public class JPAHelper {

    public static String column(BeanProperty<?, ?> property) {
        Column column = property.getAnnotation(Column.class);
        if (column == null) {
            return property.getKey();
        }
        return column.name();
    }

    public static String table(BeanModel<?> model) {
        Table table = model.getAnnotation(Table.class);
        if (table == null) {
            return model.getType().getRawType().getSimpleName();
        }
        return table.name();
    }

    public static String id(Class<?> aClass) {
        BeanModel<?> model = BeanIntrospector.create(aClass);
        for (BeanProperty<?, ?> property : model.getProperties()) {
            Id id = property.getAnnotation(Id.class);
            if (id != null) {
                return column(property);
            }
        }
        throw new RuntimeException("No ID Annotation found on: " + model);
    }

    public static String joinTableName(BeanProperty<?, ?> property, From<?> from, Join<?, ?> join) {
        JoinTable joinTable = property.getAnnotation(JoinTable.class);
        if (joinTable == null) {
            Class<?> source = from.getSource();
            BeanModel<?> sourceModel = BeanIntrospector.create(source);
            String sourceName = table(sourceModel);

            Class<?> destination = join.getSource();
            BeanModel<?> destinationModel = BeanIntrospector.create(destination);
            String destinationName = table(destinationModel);
            return sourceName + "_" + destinationName;
        }
        return joinTable.name();
    }

    public static String joinTableColumnName(BeanProperty<?, ?> property, From<?> from) {
        JoinTable joinTable = property.getAnnotation(JoinTable.class);
        if (joinTable == null) {
            String id = id(from.getSource());

            return from.destinationTableName().toLowerCase() + "_" + id;
        }
        return joinTable.joinColumns()[0].name();
    }

    public static String inverseJoinColumn(BeanProperty<?, ?> property, Join<?, ?> to) {
        JoinTable joinTable = property.getAnnotation(JoinTable.class);
        if (joinTable == null) {
            String id = id(to.getSource());
            return column(property) + "_" + id;
        }
        return joinTable.inverseJoinColumns()[0].name();
    }

    public static String joinColumn(BeanProperty<?, ?> property, Join<?, ?> to) {
        JoinColumn joinColumn = property.getAnnotation(JoinColumn.class);
        if (joinColumn == null) {
            String id = id(to.getSource());
            return column(property) + "_" + id;
        }
        return joinColumn.name();
    }

}

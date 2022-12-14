package com.anjunar.sql.builder;

import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.joins.Join;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.persistence.*;

public class JPAHelper {

    public static String column(BeanProperty<?, ?> property) {
        Column column = property.getAnnotation(Column.class);
        if (column == null) {
            return property.getKey();
        }
        return column.name();
    }

    public static String table(Class<?> aClass) {
        BeanModel<?> model = BeanIntrospector.create(aClass);
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

    public static String referencedColumnName(Class<?> aClass, BeanProperty<?,?> property) {
        JoinColumn joinColumn = property.getAnnotation(JoinColumn.class);
        if (joinColumn == null) {
            return id(aClass);
        }
        return joinColumn.referencedColumnName();
    }

    public static String joinTableReferencedColumnName(Class<?> aClass, BeanProperty<?,?> property) {
        JoinTable joinTable = property.getAnnotation(JoinTable.class);
        if (joinTable == null) {
            return id(aClass);
        }
        JoinColumn joinColumn = joinTable.joinColumns()[0];
        return joinColumn.referencedColumnName();
    }

    public static String joinTableInverseReferencedColumnName(Class<?> aClass, BeanProperty<?,?> property) {
        JoinTable joinTable = property.getAnnotation(JoinTable.class);
        if (joinTable == null) {
            return id(aClass);
        }
        JoinColumn joinColumn = joinTable.inverseJoinColumns()[0];
        return joinColumn.referencedColumnName();
    }

    public static String joinTableName(BeanProperty<?, ?> property, From<?> from, Join<?, ?> join) {
        JoinTable joinTable = property.getAnnotation(JoinTable.class);
        if (joinTable == null) {
            Class<?> source = from.getSource();
            String sourceName = table(source);

            Class<?> destination = join.getSource();
            String destinationName = table(destination);
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

    public static String joinTableColumnNameMapped(BeanProperty<?, ?> property, From<?> from) {
        JoinTable joinTable = property.getAnnotation(JoinTable.class);
        if (joinTable == null) {
            String id = id(from.getSource());

            return column(property) + "_" + id;
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

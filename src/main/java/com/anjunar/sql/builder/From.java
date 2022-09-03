package com.anjunar.sql.builder;

import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.joins.JsonJoin;
import com.anjunar.sql.builder.joins.NormalJoin;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class From<E> extends Selection<E> {

    private final Class<E> source;

    private final List<Join<E, ?>> joins = new ArrayList<>();

    private final String tableName;

    public From(Class<E> source) {
        this.source = source;

        if (source == null) {
            tableName = null;
        } else {
            BeanModel<E> beanModel = BeanIntrospector.create(source);
            Table tableAnnotation = beanModel.getAnnotation(Table.class);
            if (tableAnnotation == null) {
                tableName = source.getSimpleName();
            } else {
                tableName = tableAnnotation.name();
            }
        }
    }

    public Class<E> getSource() {
        return source;
    }

    public List<Join<E, ?>> getJoins() {
        return joins;
    }

    public String getTableName() {
        return tableName;
    }

    public String getAlias() {
        return getTableName().toLowerCase();
    }

    public <U> JsonJoin<E,U> jsonJoin(JsonJoin<E, U> join) {
        join.setParent(this);
        this.joins.add(join);
        return join;
    }

    public <U> Join<E, U> join(String attribute, NormalJoin.Type type) {
        BeanModel<E> beanModel = BeanIntrospector.create(source);
        BeanProperty<E, ?> property = beanModel.get(attribute);
        Class<?> rawType = property.getType().getRawType();

        if (Collection.class.isAssignableFrom(rawType)) {
            Class<U> collectionType = (Class<U>) property.getType().resolveType(Collection.class.getTypeParameters()[0]).getRawType();
            NormalJoin<E, U> join = new NormalJoin<>(collectionType, type);
            join.setParent(this);
            joins.add(join);
            return join;
        }

        throw new RuntimeException("Not Implemented yet");
    }

    public <X> Path<X> get(String attribute) {
        return new Path<X>(attribute, (From<X>) this);
    }

    @Override
    public String execute() {
        return "*";
    }
}

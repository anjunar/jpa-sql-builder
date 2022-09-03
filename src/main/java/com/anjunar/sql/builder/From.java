package com.anjunar.sql.builder;

import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.joins.postgres.JsonJoin;
import com.anjunar.sql.builder.joins.Join;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class From<E> extends AbstractSelection<E> {

    private final Class<E> source;

    private final List<AbstractJoin<E, ?>> joins = new ArrayList<>();

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

    public List<AbstractJoin<E, ?>> getJoins() {
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

    public <U> AbstractJoin<E, U> join(PluralAttribute<E, ?, U> attribute, Join.Type type) {
        BeanModel<E> beanModel = BeanIntrospector.create(source);
        BeanProperty<E, ?> property = beanModel.get(attribute.getName());
        Class<?> rawType = property.getType().getRawType();

        if (Collection.class.isAssignableFrom(rawType)) {
            Class<U> collectionType = (Class<U>) property.getType().resolveType(Collection.class.getTypeParameters()[0]).getRawType();
            Join<E, U> join = new Join<>(collectionType, type);
            join.setParent(this);
            joins.add(join);
            return join;
        }

        throw new RuntimeException("Not Implemented yet");
    }

    public <X,Y> Path<Y> get(SingularAttribute<X, Y> attribute) {
        return new Path<Y>(attribute, (From<Y>) this);
    }

    @Override
    public String execute(Context context) {
        return "*";
    }
}

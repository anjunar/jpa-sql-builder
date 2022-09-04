package com.anjunar.sql.builder;

import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.joins.jpa.ManyToManyJoin;
import com.anjunar.sql.builder.joins.jpa.OneToManyJoin;
import com.anjunar.sql.builder.joins.jpa.OneToOneJoin;
import com.anjunar.sql.builder.joins.postgres.JsonJoin;
import com.anjunar.sql.builder.joins.Join;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;

import java.lang.annotation.Annotation;
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
            tableName = JPAHelper.table(beanModel);
        }
    }

    public Class<E> getSource() {
        return source;
    }

    public List<AbstractJoin<E, ?>> getJoins() {
        return joins;
    }

    public String destinationTableName() {
        return tableName;
    }

    public String destinationTableAlias() {
        return destinationTableName().toLowerCase();
    }

    public <U> JsonJoin<E,U> jsonJoin(JsonJoin<E, U> join) {
        join.setParent(this);
        this.joins.add(join);
        return join;
    }

    public <U> AbstractJoin<E,U> join(SingularAttribute<E, U> attribute, Join.Type type) {
        BeanModel<E> beanModel = BeanIntrospector.create(source);
        BeanProperty<E, U> property = (BeanProperty<E, U>) beanModel.get(attribute.getName());
        Class<U> rawType = (Class<U>) property.getType().getRawType();
        OneToOne oneToOne = property.getAnnotation(OneToOne.class);
        OneToOneJoin<E, U> join = new OneToOneJoin<>(rawType, type, property);
        join.setParent(this);
        joins.add(join);
        return join;
    }

    public <U> AbstractJoin<E, U> join(PluralAttribute<E, ?, U> attribute, Join.Type type) {
        BeanModel<E> beanModel = BeanIntrospector.create(source);
        BeanProperty<E, ?> property = beanModel.get(attribute.getName());
        Class<?> rawType = property.getType().getRawType();

        if (Collection.class.isAssignableFrom(rawType)) {
            for (Annotation annotation : property.getAnnotations()) {
                switch (annotation) {
                    case OneToMany oneToMany -> {
                        Class<U> collectionType = (Class<U>) property.getType().resolveType(Collection.class.getTypeParameters()[0]).getRawType();
                        Join<E, U> join = new OneToManyJoin<>(collectionType, type, property, oneToMany.mappedBy());
                        join.setParent(this);
                        joins.add(join);
                        return join;
                    }
                    case ManyToOne manyToOne -> {
                        throw new RuntimeException("Not implemented yet");
                    }
                    case ManyToMany manyToMany -> {
                        Class<U> collectionType = (Class<U>) property.getType().resolveType(Collection.class.getTypeParameters()[0]).getRawType();
                        Join<E, U> join = new ManyToManyJoin<>(collectionType, type, property, manyToMany.mappedBy());
                        join.setParent(this);
                        joins.add(join);
                        return join;
                    }
                    default -> {
                        // No op
                    }
                }
            }
        }

        throw new RuntimeException("Not Implemented yet");
    }

    public <X,Y> Path<Y> get(SingularAttribute<X, Y> attribute) {
        return new Path<Y>(attribute, (From<Y>) this);
    }

    @Override
    public String execute(Context context) {
        return destinationTableAlias();
    }
}

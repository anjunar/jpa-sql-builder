package com.anjunar.sql.builder;

import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.sql.builder.joins.jpa.*;
import com.anjunar.sql.builder.joins.jpa.mapped.ManyToManyMappedJoin;
import com.anjunar.sql.builder.joins.jpa.mapped.OneToManyMappedJoin;
import com.anjunar.sql.builder.joins.jpa.mapped.OneToOneMappedJoin;
import com.anjunar.sql.builder.joins.postgres.JsonJoin;
import com.anjunar.sql.builder.joins.Join;
import com.google.common.base.Strings;
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
            tableName = JPAHelper.table(source);
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

    public <U> AbstractJoin<E,U> join(SingularAttribute<E, U> attribute) {
        return join(attribute, Join.Type.STANDARD);
    }

    public <U> AbstractJoin<E,U> join(SingularAttribute<E, U> attribute, Join.Type type) {
        BeanModel<E> beanModel = BeanIntrospector.create(source);
        BeanProperty<E, U> property = (BeanProperty<E, U>) beanModel.get(attribute.getName());
        Class<U> rawType = (Class<U>) property.getType().getRawType();
        for (Annotation annotation : property.getAnnotations()) {
            switch (annotation) {
                case OneToOne oneToOne -> {
                    Join<E, U> join;
                    if (Strings.isNullOrEmpty(oneToOne.mappedBy())) {
                        join = new OneToOneJoin<>(rawType, type, property);
                    } else {
                        BeanModel<U> destinationModel = BeanIntrospector.create(rawType);
                        BeanProperty<U, ?> destinationProperty = destinationModel.get(oneToOne.mappedBy());
                        join = new OneToOneMappedJoin<>(rawType, type, destinationProperty);
                    }
                    join.setParent(this);
                    joins.add(join);
                    return join;
                }
                case ManyToOne manyToOne -> {
                    ManyToOneJoin<E, U> join = new ManyToOneJoin<>(rawType, type, property);
                    join.setParent(this);
                    joins.add(join);
                    return join;
                }
                default -> {
                    // No Op
                }
            }
        }

        throw new RuntimeException("Not implemented yet");
    }

    public <U> AbstractJoin<E, U> join(PluralAttribute<E, ?, U> attribute) {
        return join(attribute, Join.Type.STANDARD);
    }

    public <U> AbstractJoin<E, U> join(PluralAttribute<E, ?, U> attribute, Join.Type type) {
        BeanModel<E> beanModel = BeanIntrospector.create(source);
        BeanProperty<E, ?> property = beanModel.get(attribute.getName());
        Class<?> rawType = property.getType().getRawType();

        if (Collection.class.isAssignableFrom(rawType)) {
            Class<U> collectionType = (Class<U>) property.getType().resolveType(Collection.class.getTypeParameters()[0]).getRawType();

            for (Annotation annotation : property.getAnnotations()) {
                switch (annotation) {
                    case OneToMany oneToMany -> {
                        Join<E, U> join;
                        if (Strings.isNullOrEmpty(oneToMany.mappedBy())) {
                            join = new OneToManyJoin<>(collectionType, type, property);
                        } else {
                            BeanModel<U> destinationModel = BeanIntrospector.create(collectionType);
                            BeanProperty<U, ?> destinationProperty = destinationModel.get(oneToMany.mappedBy());
                            join = new OneToManyMappedJoin<>(collectionType, type, destinationProperty);
                        }
                        join.setParent(this);
                        joins.add(join);
                        return join;
                    }
                    case ManyToMany manyToMany -> {
                        Join<E, U> join;
                        if (Strings.isNullOrEmpty(manyToMany.mappedBy())) {
                            join = new ManyToManyJoin<>(collectionType, type, property);
                        } else {
                            BeanModel<U> destinationModel = BeanIntrospector.create(collectionType);
                            BeanProperty<U, ?> destinationProperty = destinationModel.get(manyToMany.mappedBy());
                            join = new ManyToManyMappedJoin<>(collectionType, type, destinationProperty, property);
                        }
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

    public <Y> Path<Y> get(SingularAttribute<E, Y> attribute) {
        return new Path<>(attribute, (From<Y>) this);
    }

    @Override
    public String execute(Context context) {
        return destinationTableAlias();
    }
}

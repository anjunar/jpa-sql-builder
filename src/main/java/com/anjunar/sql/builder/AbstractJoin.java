package com.anjunar.sql.builder;

import com.anjunar.introspector.bean.BeanProperty;

public abstract class AbstractJoin<U, E> extends From<E> {

    private final BeanProperty<?, ?> property;
    private From<U> parent;

    public AbstractJoin(Class<E> collectionType, BeanProperty<?, ?> property) {
        super(collectionType);
        this.property = property;
    }

    public void setParent(From<U> parent) {
        this.parent = parent;
    }

    public From<U> getParent() {
        return parent;
    }

    public BeanProperty<?, ?> getBeanProperty() {
        return property;
    }
}

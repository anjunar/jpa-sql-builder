package com.anjunar.sql.builder;

public abstract class Join<U, E> extends From<E> {

    private From<U> parent;

    public Join(Class<E> source) {
        super(source);
    }

    public void setParent(From<U> parent) {
        this.parent = parent;
    }

    public From<U> getParent() {
        return parent;
    }

    public String execute() {
        return new StringBuilder()
                .append(getTableName())
                .append(" ")
                .append(getAlias())
                .toString();
    }

}

package com.anjunar.sql.builder;

public abstract class AbstractJoin<U, E> extends From<E> {

    private From<U> parent;

    public AbstractJoin(Class<E> source) {
        super(source);
    }

    public void setParent(From<U> parent) {
        this.parent = parent;
    }

    public From<U> getParent() {
        return parent;
    }

    public String execute(Context context) {
        return new StringBuilder()
                .append(getTableName())
                .append(" ")
                .append(getAlias())
                .toString();
    }

}

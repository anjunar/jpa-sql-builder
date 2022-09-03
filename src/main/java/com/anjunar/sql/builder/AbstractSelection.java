package com.anjunar.sql.builder;

public abstract class AbstractSelection<E> implements Expression<E> {

    public abstract String execute(Context context);

}

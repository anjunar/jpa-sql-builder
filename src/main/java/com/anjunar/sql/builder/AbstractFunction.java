package com.anjunar.sql.builder;

public abstract class AbstractFunction<E> implements Expression<E> {

    public abstract String execute(Context context);

}

package com.anjunar.sql.builder;

public abstract class AbstractPredicate<E> implements Expression<E> {

    public abstract String execute(Context context);

}

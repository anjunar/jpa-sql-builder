package com.anjunar.sql.builder;

public interface Expression<E> {

    String execute(Context context);

}

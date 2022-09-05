package com.anjunar.sql.builder;

public class Variable<E> implements Expression<E> {

    private final E value;

    public Variable(E value) {
        this.value = value;
    }

    @Override
    public String execute(Context context) {
        Integer next = context.next();
        context.mappings().put(next, value);
        return ":v" + next;
    }
}

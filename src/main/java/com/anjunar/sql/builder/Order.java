package com.anjunar.sql.builder;

public class Order {

    public enum Type {
        DESC,
        ASC

    }

    private final Expression<?> path;

    private final Type type;

    public Order(Expression<?> path, Type type) {
        this.path = path;
        this.type = type;
    }

    public String execute(Context context) {
        return new StringBuilder()
                .append(path.execute(context))
                .append(" ")
                .append(type)
                .toString();
    }

}

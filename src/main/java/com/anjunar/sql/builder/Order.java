package com.anjunar.sql.builder;

public class Order {

    public enum Type {
        DESC,
        ASC

    }

    private final Path<?> path;

    private final Type type;

    public Order(Path<?> path, Type type) {
        this.path = path;
        this.type = type;
    }

    public String execute() {
        return new StringBuilder()
                .append(path.execute())
                .append(" ")
                .append(type)
                .toString();
    }

}

package com.anjunar.sql.builder;

import com.anjunar.sql.builder.joins.postgres.JsonJoin;
import com.anjunar.sql.builder.joins.Join;
import com.anjunar.sql.builder.aggregators.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Query<E> {

    private final Class<E> result;

    private From<E> from;

    private Path<E> path;

    private Path<?> groupBy;

    private AbstractSelection<E> selection;

    private boolean distinct = false;

    private final List<Expression<?>> predicates = new ArrayList<>();

    private final List<Expression<?>> having = new ArrayList<>();

    private final List<Order> orders = new ArrayList<>();

    public Query(Class<E> result) {
        this.result = result;
    }

    public Class<E> getResult() {
        return result;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public AbstractSelection<E> getFrom() {
        return from;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Query<E> distinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public <X> From<X> from(Class<X> aClass) {
        return new From<X>(aClass);
    }

    public Query<E> select(AbstractSelection<E> selection) {
        switch (selection) {
            case From<E> from -> {
                this.from = from;
            }
            case AbstractPathSelection<E, ?> pathSelection -> {
                this.selection = pathSelection;
                this.from = (From<E>) pathSelection.getPath().getParent();
            }
            case Path<E> path -> {
                this.path = path;
                this.from = path.getParent();
            }
            default -> throw new RuntimeException("Not Implemented yet");
        }
        return this;
    }

    public <U> Query<E> where(Expression<?> predicate) {
        predicates.add(predicate);
        return this;
    }

    public String execute(Context context) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        if (distinct) {
            sql.append("distinct ");
        }
        if (Objects.nonNull(selection)) {
            sql.append(selection.execute(context));
        } else {
            if (Objects.nonNull(path)) {
                sql.append(path.execute(context));
                sql.append(" ");
            } else {
                sql.append("* ");
            }

        }
        sql.append("from ");
        sql.append(from.getTableName());
        sql.append(" ");
        sql.append(from.getAlias());

        for (AbstractJoin<?, ?> join : from.getJoins()) {
            switch (join) {
                case JsonJoin<?, ?> jsonJoin -> {
                    sql.append(", ");
                    sql.append(join.execute(context));
                }
                case Join<?,?> normalJoin -> {
                    switch (normalJoin.getType()) {
                        case LEFT -> sql.append(" left join ");
                        case RIGHT -> sql.append(" right join ");
                        case INNER -> sql.append(" inner join ");
                        case FULL -> sql.append(" full join ");
                        case NATURAL -> sql.append(" natural join ");
                    }
                    sql.append(join.execute(context));
                }
                default -> throw new RuntimeException("Not implemented");
            }
        }

        if (! predicates.isEmpty()) {
            sql.append(" where ");
            for (Expression<?> predicate : predicates) {
                sql.append(predicate.execute(context));
            }
        }

        if (groupBy != null) {
            sql.append(" group by ");
            sql.append(groupBy.execute(context));
        }

        if (! having.isEmpty()) {
            sql.append(" having ");
            for (Expression<?> predicate : having) {
                sql.append(predicate.execute(context));
            }
        }

        if (! orders.isEmpty()) {
            sql.append(" order by ");
            sql.append(String.join(", ", orders.stream().map(order -> order.execute(context)).toList()));
        }

        return sql.toString();
    }

    public Query<E> orderBy(Order... order) {
        orders.addAll(Arrays.asList(order));
        return this;
    }

    public Query<E> orderBy(List<Order> order) {
        orders.addAll(order);
        return this;
    }

    public Query<E> groupBy(Path<?> path) {
        this.groupBy = path;
        return this;
    }

    public Query<E> having(AbstractPredicate predicate) {
        having.add(predicate);
        return this;
    }

    public <X> Query<X> subQuery(Class<X> aClass) {
        return new Query<>(aClass);
    }
}

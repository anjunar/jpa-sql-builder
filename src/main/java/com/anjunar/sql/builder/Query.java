package com.anjunar.sql.builder;

import com.anjunar.sql.builder.joins.JsonJoin;
import com.anjunar.sql.builder.joins.NormalJoin;
import com.anjunar.sql.builder.aggregators.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Query<E> {

    private final Class<E> result;

    private final Context context = new Context();

    private From<E> from;

    private Path<?> groupBy;

    private Selection<E> selection;

    private boolean distinct = false;

    private final List<Predicate> predicates = new ArrayList<>();

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

    public Selection<E> getFrom() {
        return from;
    }

    public List<Predicate> getPredicates() {
        return predicates;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Context getContext() {
        return context;
    }

    public Query<E> distinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public <X> From<X> from(Class<X> aClass) {
        return new From<X>(aClass);
    }

    public Query<E> select(Selection<E> selection) {
        switch (selection) {
            case From<E> from -> {
                this.from = from;
            }
            case CountSelection<E,?> countSelection -> {
                this.selection = countSelection;
                this.from = (From<E>) countSelection.getFrom();
            }
            case PathSelection<E, ?> pathSelection -> {
                this.selection = pathSelection;
                this.from = (From<E>) pathSelection.getPath().getParent();
            }
            default -> throw new RuntimeException("Not Implemented yet");
        }
        return this;
    }

    public <U> Query<E> where(Predicate predicate) {
        predicates.add(predicate);
        return this;
    }

    public String execute() {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        if (distinct) {
            sql.append("distinct ");
        }
        if (Objects.nonNull(selection)) {
            sql.append(selection.execute());
        } else {
            sql.append("* ");
        }
        sql.append("from ");
        sql.append(from.getTableName());
        sql.append(" ");
        sql.append(from.getAlias());

        for (Join<?, ?> join : from.getJoins()) {
            switch (join) {
                case JsonJoin<?, ?> jsonJoin -> {
                    sql.append(", ");
                    sql.append(join.execute());
                }
                case NormalJoin<?,?> normalJoin -> {
                    switch (normalJoin.getType()) {
                        case LEFT -> sql.append(" left join ");
                        case RIGHT -> sql.append(" right join ");
                        case INNER -> sql.append(" inner join ");
                        case FULL -> sql.append(" full join ");
                        case NATURAL -> sql.append(" natural join ");
                    }
                    sql.append(join.execute());
                }
                default -> throw new RuntimeException("Not implemented");
            }
        }

        if (! predicates.isEmpty()) {
            sql.append(" where ");
            for (Predicate predicate : predicates) {
                sql.append(predicate.execute(context));
            }
        }

        if (groupBy != null) {
            sql.append(" group by ");
            sql.append(groupBy.execute());
        }

        if (! orders.isEmpty()) {
            sql.append(" order by ");
            sql.append(String.join(", ", orders.stream().map(Order::execute).toList()));
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
}

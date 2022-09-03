package com.anjunar.sql.builder;

import com.anjunar.sql.builder.joins.JsonJoin;
import com.anjunar.sql.builder.predicates.*;
import com.anjunar.sql.builder.aggregators.*;

public class SqlBuilder {

    public static <E> String execute(Query<E> query) {
        query.setContext(new Context());
        return query.execute();
    }

    public static <E> Query<E> query(Class<E> result) {
        return new Query<>(result);
    }

    public static <E, U> JsonJoin<E, U> jsonArray(String attribute, String property) {
        return new JsonJoin<>(attribute, property);
    }

    public static <E,U> Predicate jsonEqual(JsonJoin<E, U> join, String property, String value) {
        return new JsonEqualPredicate<>(join, property, value);
    }

    public static <E> LevenstheinPredicate<E> levensthein(Path<E> attribute, String value) {
        return new LevenstheinPredicate<>(value, attribute);
    }

    public static <E> LikePredicate<E> like(Path<E> path, String value) {
        return new LikePredicate<>(value, path);
    }

    public static AndPredicate and(Predicate... predicates) {
        return new AndPredicate(predicates);
    }

    public static OrPredicate or(Predicate... predicates) {
        return new OrPredicate(predicates);
    }

    public static Order asc(Path<?> path) {
        return new Order(path, Order.Type.ASC);
    }

    public static Order desc(Path<?> path) {
        return new Order(path, Order.Type.DESC);
    }

    public static <E> IsNullPredicate<E> isNull(Path<E> path) {
        return new IsNullPredicate<E>(path);
    }

    public static <E> IsNotNullPredicate<E> isNotNull(Path<E> path) {
        return new IsNotNullPredicate<E>(path);
    }
    public static <E extends Number, X> Selection<E> max(Path<X> path) {
        return new MaxSelection<>(path);
    }
    public static <E extends Number, X> Selection<E> min(Path<X> path) {
        return new MinSelection<>(path);
    }

    public static <E extends Number, X> Selection<E> count(Path<X> path) {
        return new CountSelection<>(path);
    }

    public static <E extends Number, X> Selection<E> avg(Path<X> path) {
        return new AvgSelection<>(path);
    }

    public static <E extends Number, X> Selection<E> sum(Path<X> path) {
        return new SumSelection<>(path);
    }

    public static <E> BetweenPredicate<E> between(Path<E> path, Comparable<?> from, Comparable<?> to) {
        return new BetweenPredicate<>(path, from, to);
    }

    public static <E extends Comparable<?>> GreaterThanPredicate<E> greaterThan(Selection<E> selection, Comparable<?> value) {
        return new GreaterThanPredicate<>(selection, value);
    }

    public static <E> ExistPredicate exist(Query<E> select) {
        return new ExistPredicate<E>(select);
    }

    public static <E,U> EqualPredicate equal(Path<E> lhs, Path<U> rhs) {
        return new EqualPredicate(lhs, rhs);
    }
}

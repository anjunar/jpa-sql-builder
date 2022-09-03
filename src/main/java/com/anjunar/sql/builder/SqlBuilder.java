package com.anjunar.sql.builder;

import com.anjunar.sql.builder.functions.advanced.IsNotNullFunction;
import com.anjunar.sql.builder.functions.advanced.IsNullFunction;
import com.anjunar.sql.builder.joins.postgres.JsonJoin;
import com.anjunar.sql.builder.aggregators.*;
import com.anjunar.sql.builder.predicates.comparison.EqualPredicate1;
import com.anjunar.sql.builder.predicates.comparison.EqualPredicate2;
import com.anjunar.sql.builder.predicates.comparison.GreaterThanPredicate;
import com.anjunar.sql.builder.functions.advanced.CoalesceFunction;
import com.anjunar.sql.builder.predicates.logical.*;
import com.anjunar.sql.builder.functions.postgres.JsonEqualFunction;
import com.anjunar.sql.builder.functions.postgres.LevenstheinFunction;
import jakarta.persistence.metamodel.SingularAttribute;

public class SqlBuilder {

    public static <E> String execute(Query<E> query) {
        return query.execute(new Context());
    }

    public static <E> Query<E> query(Class<E> result) {
        return new Query<>(result);
    }

    public static <E, U> JsonJoin<E, U> jsonArray(SingularAttribute<E,U> attribute, String property) {
        return new JsonJoin<>(attribute, property);
    }

    public static <E,U> JsonEqualFunction<E,U> jsonEqual(JsonJoin<E, U> join, String property, String value) {
        return new JsonEqualFunction<>(join, property, value);
    }

    public static <E> LevenstheinFunction<E> levensthein(Expression<E> attribute, String value) {
        return new LevenstheinFunction<>(value, attribute);
    }

    public static <E> LikePredicate<E> like(Expression<E> path, String value) {
        return new LikePredicate<>(value, path);
    }

    public static AndPredicate and(Expression<?>... predicates) {
        return new AndPredicate(predicates);
    }

    public static OrPredicate or(Expression<?>... predicates) {
        return new OrPredicate(predicates);
    }

    public static Order asc(Expression<?> path) {
        return new Order(path, Order.Type.ASC);
    }

    public static Order desc(Expression<?> path) {
        return new Order(path, Order.Type.DESC);
    }

    public static <E> IsNullFunction<E> isNull(Expression<E> path) {
        return new IsNullFunction<E>(path);
    }

    public static <E> IsNotNullFunction<E> isNotNull(Expression<E> path) {
        return new IsNotNullFunction<E>(path);
    }
    public static <E extends Number, X> AbstractSelection<E> max(Expression<X> path) {
        return new MaxSelection<>(path);
    }
    public static <E extends Number, X> AbstractSelection<E> min(Expression<X> path) {
        return new MinSelection<>(path);
    }

    public static <E extends Number, X> AbstractSelection<E> count(Expression<X> path) {
        return new CountSelection<>(path);
    }

    public static <E extends Number, X> AbstractSelection<E> avg(Expression<X> path) {
        return new AvgSelection<>(path);
    }

    public static <E extends Number, X> AbstractSelection<E> sum(Expression<X> path) {
        return new SumSelection<>(path);
    }

    public static <E> BetweenPredicate<E> between(Expression<E> path, Comparable<?> from, Comparable<?> to) {
        return new BetweenPredicate<>(path, from, to);
    }

    public static <E extends Comparable<?>> GreaterThanPredicate<E> greaterThan(AbstractSelection<E> selection, Comparable<?> value) {
        return new GreaterThanPredicate<>(selection, value);
    }

    public static <E> ExistPredicate<E> exist(Query<E> select) {
        return new ExistPredicate<E>(select);
    }

    public static <E,U> EqualPredicate1<E, U> equal(Expression<E> lhs, Expression<U> rhs) {
        return new EqualPredicate1<>(lhs, rhs);
    }
    public static <E,U> EqualPredicate2<E, U> equal(Expression<E> lhs, Object value) {
        return new EqualPredicate2<>(lhs, value);
    }

    public static <E, U> AnyPredicate<E,U> any(Expression<E> path, Query<U> query) {
        return new AnyPredicate<>(path, query);
    }

    public static <E, U> AllPredicate<E,U> all(Expression<E> path, Query<U> query) {
        return new AllPredicate<>(path, query);
    }

    public static <E> CoalesceFunction<E> coalesce(Expression<E> path, Object value) {
        return new CoalesceFunction<>(value, path);
    }
}

package com.anjunar.sql.builder;

import com.anjunar.sql.builder.aggregators.*;
import com.anjunar.sql.builder.functions.advanced.CoalesceFunction;
import com.anjunar.sql.builder.functions.advanced.IsNotNullFunction;
import com.anjunar.sql.builder.functions.advanced.IsNullFunction;
import com.anjunar.sql.builder.functions.postgres.JsonEqualFunction;
import com.anjunar.sql.builder.functions.postgres.LevenstheinFunction;
import com.anjunar.sql.builder.functions.string.*;
import com.anjunar.sql.builder.joins.postgres.JsonJoin;
import com.anjunar.sql.builder.predicates.ArithmeticPredicate;
import com.anjunar.sql.builder.predicates.BitwisePredicate;
import com.anjunar.sql.builder.predicates.ComparisonPredicate;
import com.anjunar.sql.builder.predicates.CompoundPredicate;
import com.anjunar.sql.builder.predicates.logical.*;
import jakarta.persistence.metamodel.SingularAttribute;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlBuilder {

    public static <E> String execute(Query<E> query) {
        return query.execute(new Context());
    }

    public static <E> Query<E> query(Class<E> result) {
        return new Query<>(result);
    }

    public static Order asc(Expression<?> path) {
        return new Order(path, Order.Type.ASC);
    }

    public static Order desc(Expression<?> path) {
        return new Order(path, Order.Type.DESC);
    }

    public static <E> Variable<E> variable(E value) {
        return new Variable<E>(value);
    }


//////////////////////////////////////////////////Aggregators///////////////////////////////////////////////////////////

    public static <E extends Number, X> AbstractSelection<E> avg(Expression<X> path) {
        return new AvgSelection<>(path);
    }

    public static <E extends Number, X> AbstractSelection<E> count(Expression<X> path) {
        return new CountSelection<>(path);
    }

    public static <E extends Number, X> AbstractSelection<E> max(Expression<X> path) {
        return new MaxSelection<>(path);
    }

    public static <E extends Number, X> AbstractSelection<E> min(Expression<X> path) {
        return new MinSelection<>(path);
    }

    public static <E extends Number, X> AbstractSelection<E> sum(Expression<X> path) {
        return new SumSelection<>(path);
    }

//////////////////////////////////////////////////Advanced Functions////////////////////////////////////////////////////

    public static <E> CoalesceFunction<E> coalesce(Expression<E> path, Expression<E> value) {
        return new CoalesceFunction<>(value, path);
    }

    public static <E> IsNullFunction<E> isNull(Expression<E> path) {
        return new IsNullFunction<E>(path);
    }

    public static <E> IsNotNullFunction<E> isNotNull(Expression<E> path) {
        return new IsNotNullFunction<E>(path);
    }


////////////////////////////////////////////////Postgres Functions//////////////////////////////////////////////////////

    public static <E, U> JsonEqualFunction<E, U> jsonEqual(JsonJoin<E, U> join, String property, Expression<String> value) {
        return new JsonEqualFunction<>(join, property, value);
    }

    public static LevenstheinFunction levensthein(Expression<String> attribute, Expression<String> value) {
        return new LevenstheinFunction(value, attribute);
    }

//////////////////////////////////////////////String Functions//////////////////////////////////////////////////////////

    public static ASCIIFunction ascii(Expression<String> attribute) {
        return new ASCIIFunction(attribute);
    }

    public static CharFunction aChar(Expression<Integer> value) {
        return new CharFunction(value);
    }

    public static ConcatFunction concat(Expression<String>... values) {
        return new ConcatFunction(Arrays.asList(values));
    }

    public static ConcatWsFunction concatWs(String separator, Expression<String>... values) {
        return new ConcatWsFunction(separator, Arrays.asList(values));
    }

    public static DataLength dataLength(Expression<String> value) {
        return new DataLength(value);
    }

//////////////////////////////////////////////Postgres Joins////////////////////////////////////////////////////////////

    public static <E, U> JsonJoin<E, U> jsonArray(SingularAttribute<E, U> attribute, String property) {
        return new JsonJoin<>(attribute, property);
    }

//////////////////////////////////////////////////////Comparison////////////////////////////////////////////////////////

    public static <E, U> ComparisonPredicate<E, U> equal(Expression<E> lhs, Expression<U> rhs) {
        return new ComparisonPredicate<>(lhs, rhs, ComparisonPredicate.Type.EQUAL);
    }


    public static <E, U> ComparisonPredicate<E, U> greaterThan(Expression<E> lhs, Expression<U> rhs) {
        return new ComparisonPredicate<>(lhs, rhs, ComparisonPredicate.Type.GREATER_THAN);
    }


    public static <E, U> ComparisonPredicate<E, U> lessThan(Expression<E> lhs, Expression<U> rhs) {
        return new ComparisonPredicate<>(lhs, rhs, ComparisonPredicate.Type.LESS_THAN);
    }


    public static <E, U> ComparisonPredicate<E, U> greaterThanOrEqual(Expression<E> lhs, Expression<U> rhs) {
        return new ComparisonPredicate<>(lhs, rhs, ComparisonPredicate.Type.GREATER_THAN_OR_EQUAL);
    }


    public static <E, U> ComparisonPredicate<E, U> lessThanOrEqual(Expression<E> lhs, Expression<U> rhs) {
        return new ComparisonPredicate<>(lhs, rhs, ComparisonPredicate.Type.LESS_THAN_OR_EQUAL);
    }


    public static <E, U> ComparisonPredicate<E, U> notEqualTo(Expression<E> lhs, Expression<U> rhs) {
        return new ComparisonPredicate<>(lhs, rhs, ComparisonPredicate.Type.NOT_EQUAL_TO);
    }

////////////////////////////////////////////////////////Arithmetic//////////////////////////////////////////////////////


    public static ArithmeticPredicate add(Expression<? extends Number> lhs, Expression<? extends Number> rhs) {
        return new ArithmeticPredicate(lhs, rhs, ArithmeticPredicate.Type.ADD);
    }


    public static ArithmeticPredicate subtract(Expression<? extends Number> lhs, Expression<? extends Number> rhs) {
        return new ArithmeticPredicate(lhs, rhs, ArithmeticPredicate.Type.SUBTRACT);
    }


    public static ArithmeticPredicate divide(Expression<? extends Number> lhs, Expression<? extends Number> rhs) {
        return new ArithmeticPredicate(lhs, rhs, ArithmeticPredicate.Type.DIVIDE);
    }


    public static ArithmeticPredicate multiply(Expression<? extends Number> lhs, Expression<? extends Number> rhs) {
        return new ArithmeticPredicate(lhs, rhs, ArithmeticPredicate.Type.MULTIPLY);
    }


    public static ArithmeticPredicate modulo(Expression<? extends Number> lhs, Expression<? extends Number> rhs) {
        return new ArithmeticPredicate(lhs, rhs, ArithmeticPredicate.Type.MODULO);
    }

///////////////////////////////////////////////////////Bitwise//////////////////////////////////////////////////////////

    public static BitwisePredicate bitwiseAnd(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new BitwisePredicate(lhs, rhs, BitwisePredicate.Type.BITWISE_AND);
    }


    public static BitwisePredicate bitwiseOr(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new BitwisePredicate(lhs, rhs, BitwisePredicate.Type.BITWISE_OR);
    }


    public static BitwisePredicate bitwiseExclusiveOr(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new BitwisePredicate(lhs, rhs, BitwisePredicate.Type.BITWISE_EXCLUSIVE_OR);
    }

//////////////////////////////////////////////////////Compound//////////////////////////////////////////////////////////


    public static CompoundPredicate addEquals(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new CompoundPredicate(lhs, rhs, CompoundPredicate.Type.ADD_EQUALS);
    }

    public static CompoundPredicate subtractEquals(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new CompoundPredicate(lhs, rhs, CompoundPredicate.Type.SUBTRACT_EQUALS);
    }


    public static CompoundPredicate multiplyEquals(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new CompoundPredicate(lhs, rhs, CompoundPredicate.Type.MULTIPLY_EQUALS);
    }


    public static CompoundPredicate divideEquals(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new CompoundPredicate(lhs, rhs, CompoundPredicate.Type.DIVIDE_EQUALS);
    }


    public static CompoundPredicate moduloEquals(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new CompoundPredicate(lhs, rhs, CompoundPredicate.Type.MODULO_EQUALS);
    }

    public static CompoundPredicate bitwiseAndEquals(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new CompoundPredicate(lhs, rhs, CompoundPredicate.Type.BITWISE_AND_EQUALS);
    }


    public static CompoundPredicate bitwiseOrEquals(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new CompoundPredicate(lhs, rhs, CompoundPredicate.Type.BITWISE_OR_EQUALS);
    }

    public static CompoundPredicate bitwiseExclusiveOrEquals(Expression<? extends Comparable<?>> lhs, Expression<? extends Comparable<?>> rhs) {
        return new CompoundPredicate(lhs, rhs, CompoundPredicate.Type.BITWISE_EXCLUSIVE_OR_EQUALS);
    }


/////////////////////////////////////////////////////////Logical////////////////////////////////////////////////////////

    public static <E, U> AllPredicate<E, U> all(Expression<E> path, Query<U> query) {
        return new AllPredicate<>(path, query);
    }

    public static AndPredicate and(Expression<?>... predicates) {
        return new AndPredicate(predicates);
    }

    public static <E, U> AnyPredicate<E, U> any(Expression<E> path, Query<U> query) {
        return new AnyPredicate<>(path, query);
    }

    public static <E> BetweenPredicate<E> between(Expression<E> path, Expression<Comparable<?>> from, Expression<Comparable<?>> to) {
        return new BetweenPredicate<>(path, from, to);
    }

    public static <E> ExistPredicate<E> exist(Query<E> select) {
        return new ExistPredicate<E>(select);
    }


    public static <E> LikePredicate<E> like(Expression<E> path, Expression<String> value) {
        return new LikePredicate<>(value, path);
    }

    public static OrPredicate or(Expression<?>... predicates) {
        return new OrPredicate(predicates);
    }

    public static NotPredicate not(Expression<? extends Comparable<?>> path) {
        return new NotPredicate(path);
    }


}

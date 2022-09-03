package com.anjunar.sql.builder;

import com.anjunar.sql.builder.entities.*;
import com.anjunar.sql.builder.joins.JsonJoin;
import com.anjunar.sql.builder.joins.NormalJoin;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.anjunar.sql.builder.SqlBuilder.*;

public class Tests {

    @BeforeAll
    public static void startUp() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test");
    }

    @Test
    public void testGroupBy() {
        Query<Long> query = query(Long.class);
        From<Address> from = query.from(Address.class);

        query.select(count(from.get(Address_.ID)))
                .groupBy(from.get(Address_.COUNTRY))
                .orderBy(asc(from.get(Address_.COUNTRY)));

        String execute = execute(query);

        String result = "select count(address.id) from Address address group by address.country order by address.country ASC";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testHaving() {
        Query<Long> query = query(Long.class);
        From<Address> from = query.from(Address.class);

        query.select(count(from.get(Address_.COUNTRY)))
                .groupBy(from.get(Address_.COUNTRY))
                .having(greaterThan(count(from.get(Address_.COUNTRY)), 5));

        String execute = execute(query);

        String result = "select count(address.country) from Address address group by address.country having count(address.country) > :1";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testJsonJoin() {
        Query<Category> query = query(Category.class);
        From<Category> from = query.from(Category.class);

        JsonJoin<Category, Translations> join = from.jsonJoin(jsonArray(Category_.NAME, "translations"));

        query.select(from).where(
                jsonEqual(join, "text", "Everybody")
        );

        String execute = execute(query);

        String result = "select * from Category category, json_array_elements(category.name -> 'translations') translations where translations ->> 'text' = 'Everybody'";

        Assertions.assertEquals(execute, result);

    }

    @Test
    public void testOrderBy() {
        Query<Category> query = query(Category.class);
        From<Category> from = query.from(Category.class);

        query.select(from)
                .orderBy(asc(from.get(Category_.ID)), desc(from.get(Category_.DESCRIPTION)));

        String execute = execute(query);

        String result = "select * from Category category order by category.id ASC, category.description DESC";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testIsNull() {
        Query<Category> query = query(Category.class);
        From<Category> from = query.from(Category.class);

        query.select(from).where(isNull(from.get(Category_.DESCRIPTION)));

        String execute = execute(query);

        String result = "select * from Category category where category.description is null";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testIsNotNull() {
        Query<Category> query = query(Category.class);
        From<Category> from = query.from(Category.class);

        query.select(from).where(isNotNull(from.get(Category_.DESCRIPTION)));

        String execute = execute(query);

        String result = "select * from Category category where category.description is not null";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testMax() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(max(from.get(Category_.ID)));

        String execute = execute(query);

        String result = "select max(category.id) from Category category";

        Assertions.assertEquals(execute, result);

    }

    @Test
    public void testMin() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(min(from.get(Category_.ID)));

        String execute = execute(query);

        String result = "select min(category.id) from Category category";

        Assertions.assertEquals(execute, result);

    }

    @Test
    public void testCount() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(count(from.get(Category_.ID)));

        String execute = execute(query);

        String result = "select count(category.id) from Category category";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testAvg() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(avg(from.get(Category_.ID)));

        String execute = execute(query);

        String result = "select avg(category.id) from Category category";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testSum() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(SqlBuilder.sum(from.get(Category_.ID)));

        String execute = execute(query);

        String result = "select sum(category.id) from Category category";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testDistinct() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).distinct(true);

        String execute = execute(query);

        String result = "select distinct * from Person person";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testLevenstheinAndLike() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                and(
                        levensthein(from.get(Person_.FIRST_NAME), "Patrik"),
                        like(from.get(Person_.FIRST_NAME), "Patrik%")
                )

        );

        String execute = execute(query);

        String result = "select * from Person person where levensthein(person.firstName, :1) and person.firstName like :2";

        Assertions.assertEquals(execute, result);

    }

    @Test
    public void testNaturalJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        Join<Person, Address> join = from.join(Person_.ADDRESSES, NormalJoin.Type.NATURAL);

        query.select(from).where(
                like(join.get(Address_.STREET), "Strasse")
        );

        String execute = execute(query);

        String result = "select * from Person person natural join Address address where address.street like :1";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testLeftJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        Join<Person, Address> join = from.join(Person_.ADDRESSES, NormalJoin.Type.LEFT);

        query.select(from).where(
                like(join.get(Address_.STREET), "Strasse")
        );

        String execute = execute(query);

        String result = "select * from Person person left join Address address where address.street like :1";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testRightJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        Join<Person, Address> join = from.join(Person_.ADDRESSES, NormalJoin.Type.RIGHT);

        query.select(from).where(
                like(join.get(Address_.STREET), "Strasse")
        );

        String execute = execute(query);

        String result = "select * from Person person right join Address address where address.street like :1";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testInnerJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        Join<Person, Address> join = from.join(Person_.ADDRESSES, NormalJoin.Type.INNER);

        query.select(from).where(
                like(join.get(Address_.STREET), "Strasse")
        );

        String execute = execute(query);

        String result = "select * from Person person inner join Address address where address.street like :1";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testFullJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        Join<Person, Address> join = from.join(Person_.ADDRESSES, NormalJoin.Type.FULL);

        query.select(from).where(
                like(join.get(Address_.STREET), "Strasse")
        );

        String execute = execute(query);

        String result = "select * from Person person full join Address address where address.street like :1";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testIn() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(from.get(Person_.FIRST_NAME).in("Patrick", "Aleksander"));

        String execute = execute(query);

        String result = "select * from Person person where person.firstName in ( :1)";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testBetween() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                between(
                        from.get(Person_.BIRTHDATE),
                        LocalDate.of(1980, 1, 1),
                        LocalDate.of(1981, 1, 1)
                )
        );

        String execute = execute(query);

        String result = "select * from Person person where person.birthdate between :1 and :2";

        Assertions.assertEquals(execute, result);
    }

    @Test
    public void testExist() {

        Query<Supplier> query = query(Supplier.class);
        From<Supplier> from = query.from(Supplier.class);

        Query<Long> subQuery = query.subQuery(Long.class);
        From<Product> subFrom = subQuery.from(Product.class);

        query.select(from).where(
                exist(
                        subQuery.select(subFrom.get(Product_.ID)).where(
                                equal(subFrom.get(Product_.SUPPLIER), from.get(Supplier_.ID))
                        )
                )
        );

        String execute = execute(query);

        String result = "select * from Supplier supplier where exists (select product.id from Product product where product.supplier = supplier.id)";

        Assertions.assertEquals(execute, result);

    }


}

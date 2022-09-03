package com.anjunar.sql.builder;

import com.anjunar.sql.builder.joins.NormalJoin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.anjunar.sql.builder.SqlBuilder.*;

public class PersonTest {

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
                SqlBuilder.between(
                        from.get(Person_.BIRTHDATE),
                        LocalDate.of(1980, 1, 1),
                        LocalDate.of(1981, 1, 1)
                )
        );

        String execute = execute(query);

        String result = "select * from Person person where person.birthdate between :1 and :2";

        Assertions.assertEquals(execute, result);
    }


}

package com.anjunar.sql.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.anjunar.sql.builder.SqlBuilder.*;

public class AddressTest {

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

}

package com.anjunar.sql.builder;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void testGroupBy() {
        Query<Number> query = SqlBuilder.query(Number.class);
        From<Address> from = query.from(Address.class);

        query.select(SqlBuilder.count(from))
                .groupBy(from.get(Address_.COUNTRY))
                .orderBy(SqlBuilder.asc(from.get(Address_.COUNTRY)));

        String execute = SqlBuilder.execute(query);

        System.out.println(execute);
    }

}

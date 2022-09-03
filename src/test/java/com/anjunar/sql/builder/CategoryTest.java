package com.anjunar.sql.builder;

import com.anjunar.sql.builder.joins.JsonJoin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.anjunar.sql.builder.SqlBuilder.*;

public class CategoryTest {

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


}

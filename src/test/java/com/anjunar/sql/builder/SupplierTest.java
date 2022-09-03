package com.anjunar.sql.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.anjunar.sql.builder.SqlBuilder.*;

public class SupplierTest {

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

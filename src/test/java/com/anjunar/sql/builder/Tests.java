package com.anjunar.sql.builder;

import com.anjunar.sql.builder.entities.*;
import com.anjunar.sql.builder.joins.Join;
import com.anjunar.sql.builder.joins.postgres.JsonJoin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
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

        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Person patrick = new Person();
        patrick.setFirstName("Patrick");
        patrick.setLastName("Bittner");
        patrick.setBirthdate(LocalDate.of(1980, 1, 4));

        entityManager.persist(patrick);

        Address beim = new Address();
        beim.setStreet("Beim alten Schützenhof 28");
        beim.setZipCode("22083");
        beim.setState("Hamburg");
        beim.setCountry("Germany");

        Address test = new Address();
        test.setStreet("test");
        test.setState("test");
        test.setZipCode("test");
        test.setCountry("test");

        patrick.getAddresses().add(beim);
        patrick.getAddresses().add(test);

        entityManager.persist(beim);
        entityManager.persist(test);

        transaction.commit();

        System.out.println("Inserted Data");

        String sql = "select p.* from PERSON p join PERSON_ADDRESS PA join ADDRESS A";

        Person singleResult = (Person) entityManager.createNativeQuery(sql, Person.class)
                .getSingleResult();

        System.out.println(singleResult);
    }

    @Test
    public void testGroupBy() {
        Query<Long> query = query(Long.class);
        From<Address> from = query.from(Address.class);

        query.select(count(from.get(Address_.id)))
                .groupBy(from.get(Address_.country))
                .orderBy(asc(from.get(Address_.country)));

        String execute = execute(query);

        String result = "select count(address.id) from Address address group by address.country order by address.country ASC";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testHaving() {
        Query<Long> query = query(Long.class);
        From<Address> from = query.from(Address.class);

        query.select(count(from.get(Address_.country)))
                .groupBy(from.get(Address_.country))
                .having(greaterThan(count(from.get(Address_.country)), variable(5)));

        String execute = execute(query);

        String result = "select count(address.country) from Address address group by address.country having count(address.country) > :v1";
        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testJsonJoin() {
        Query<Category> query = query(Category.class);
        From<Category> from = query.from(Category.class);

        JsonJoin<Category, Translations> join = from.jsonJoin(jsonArray(Category_.name, "translations"));

        query.select(from).where(
                jsonEqual(join, "text", variable("Everybody"))
        );

        String execute = execute(query);

        String result = "select category.* from Category category, json_array_elements(category.name -> 'translations') translations where translations ->> 'text' = :v1";

        Assertions.assertEquals(result, execute);

    }

    @Test
    public void testOrderBy() {
        Query<Category> query = query(Category.class);
        From<Category> from = query.from(Category.class);

        query.select(from)
                .orderBy(asc(from.get(Category_.id)), desc(from.get(Category_.description)));

        String execute = execute(query);

        String result = "select category.* from Category category order by category.id ASC, category.description DESC";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testIsNull() {
        Query<Category> query = query(Category.class);
        From<Category> from = query.from(Category.class);

        query.select(from).where(isNull(from.get(Category_.description)));

        String execute = execute(query);

        String result = "select category.* from Category category where category.description is null";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testIsNotNull() {
        Query<Category> query = query(Category.class);
        From<Category> from = query.from(Category.class);

        query.select(from).where(isNotNull(from.get(Category_.description)));

        String execute = execute(query);

        String result = "select category.* from Category category where category.description is not null";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testMax() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(max(from.get(Category_.id)));

        String execute = execute(query);

        String result = "select max(category.id) from Category category";

        Assertions.assertEquals(result, execute);

    }

    @Test
    public void testMin() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(min(from.get(Category_.id)));

        String execute = execute(query);

        String result = "select min(category.id) from Category category";

        Assertions.assertEquals(result, execute);

    }

    @Test
    public void testCount() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(count(from.get(Category_.id)));

        String execute = execute(query);

        String result = "select count(category.id) from Category category";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testAvg() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(avg(from.get(Category_.id)));

        String execute = execute(query);

        String result = "select avg(category.id) from Category category";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testSum() {
        Query<Number> query = query(Number.class);
        From<Category> from = query.from(Category.class);

        query.select(sum(from.get(Category_.id)));

        String execute = execute(query);

        String result = "select sum(category.id) from Category category";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testDistinct() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).distinct(true);

        String execute = execute(query);

        String result = "select distinct person.* from Person person";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testLevenstheinAndLike() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                and(
                        levensthein(from.get(Person_.firstName), variable("Patrik")),
                        like(from.get(Person_.firstName), variable("test"))
                )

        );

        String execute = execute(query);

        String result = "select person.* from Person person where levensthein(person.firstName, :v1) and person.firstName like :v2";

        Assertions.assertEquals(result, execute);

    }

    @Test
    public void testNaturalJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        AbstractJoin<Person, Address> join = from.join(Person_.addresses, Join.Type.NATURAL);

        query.select(from).where(
                like(join.get(Address_.street), variable("test"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person natural join Person_Address person_address on person.id = person_address.person_id join Address address on address.id = person_address.addresses_id where address.street like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testLeftJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        AbstractJoin<Person, Address> join = from.join(Person_.addresses, Join.Type.LEFT);

        query.select(from).where(
                like(join.get(Address_.street), variable("test"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person left join Person_Address person_address on person.id = person_address.person_id join Address address on address.id = person_address.addresses_id where address.street like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testRightJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        AbstractJoin<Person, Address> join = from.join(Person_.addresses, Join.Type.RIGHT);

        query.select(from).where(
                like(join.get(Address_.street), variable("test"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person right join Person_Address person_address on person.id = person_address.person_id join Address address on address.id = person_address.addresses_id where address.street like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testInnerJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        AbstractJoin<Person, Address> join = from.join(Person_.addresses, Join.Type.INNER);

        query.select(from).where(
                like(join.get(Address_.street), variable("test"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person inner join Person_Address person_address on person.id = person_address.person_id join Address address on address.id = person_address.addresses_id where address.street like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testFullJoin() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        AbstractJoin<Person, Address> join = from.join(Person_.addresses, Join.Type.FULL);

        query.select(from).where(
                like(join.get(Address_.street), variable("test"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person full join Person_Address person_address on person.id = person_address.person_id join Address address on address.id = person_address.addresses_id where address.street like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testIn() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(from.get(Person_.firstName).in("Patrick", "Aleksander"));

        String execute = execute(query);

        String result = "select person.* from Person person where person.firstName in ( :1)";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testBetween() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                between(
                        from.get(Person_.birthdate),
                        variable(LocalDate.of(1980, 1, 1)),
                        variable(LocalDate.of(1981, 1, 1))
                )
        );

        String execute = execute(query);

        String result = "select person.* from Person person where person.birthdate between :v1 and :v2";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testExist() {

        Query<Supplier> query = query(Supplier.class);
        From<Supplier> from = query.from(Supplier.class);

        Query<Long> subQuery = query.subQuery(Long.class);
        From<Product> subFrom = subQuery.from(Product.class);

        query.select(from).where(
                exist(
                        subQuery.select(subFrom.get(Product_.id)).where(
                                equal(subFrom.get(Product_.supplier), from.get(Supplier_.id))
                        )
                )
        );

        String execute = execute(query);

        String result = "select supplier.* from Supplier supplier where exists (select product.id from Product product where product.supplier = supplier.id)";

        Assertions.assertEquals(result, execute);

    }

    @Test
    public void testAny() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        Query<Long> subQuery = query.subQuery(Long.class);
        From<OrderDetail> subFrom = query.from(OrderDetail.class);

        query.select(from).where(
                any(
                        from.get(Product_.id),
                        subQuery.select(subFrom.get(OrderDetail_.id)).where(
                                equal(subFrom.get(OrderDetail_.quantity), variable(10))
                        )
                )
        );

        String execute = execute(query);

        String result = "select product.* from Product product where product.id = any (select orderdetail.id from OrderDetail orderdetail where orderdetail.quantity = :v1)";

        Assertions.assertEquals(result, execute);

    }

    @Test
    public void testAll() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        Query<Long> subQuery = query.subQuery(Long.class);
        From<OrderDetail> subFrom = query.from(OrderDetail.class);

        query.select(from).where(
                all(
                        from.get(Product_.id),
                        subQuery.select(subFrom.get(OrderDetail_.id)).where(
                                equal(subFrom.get(OrderDetail_.quantity), variable(10))
                        )
                )
        );

        String execute = execute(query);

        String result = "select product.* from Product product where product.id = all (select orderdetail.id from OrderDetail orderdetail where orderdetail.quantity = :v1)";

        Assertions.assertEquals(result, execute);

    }

    @Test
    public void testCoalesce() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        query.select(from).where(
                equal(coalesce(from.get(Product_.name), variable("")), variable("test"))
        );

        String execute = execute(query);

        String result = "select product.* from Product product where coalesce(product.name, :v1) = :v2";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testAscii() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        query.select(from).where(
                equal(ascii(from.get(Product_.name)), variable("A"))
        );

        String execute = execute(query);

        String result = "select product.* from Product product where ASCII(product.name) = :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testArithmetic() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        query.select(from).where(
                add(from.get(Product_.price), variable(3))
        );

        String execute = execute(query);

        String result = "select product.* from Product product where product.price + :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testBitwise() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        query.select(from).where(
                bitwiseAnd(from.get(Product_.price), variable(3))
        );

        String execute = execute(query);

        String result = "select product.* from Product product where product.price & :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testCompound() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        query.select(from).where(
                addEquals(from.get(Product_.price), variable(3))
        );

        String execute = execute(query);
        String result = "select product.* from Product product where product.price += :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testNot() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        query.select(from).where(
                like(not(from.get(Product_.name)), variable("test"))
        );

        String execute = execute(query);
        String result = "select product.* from Product product where product.name not like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testOneToOneJoin() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        AbstractJoin<Product, Supplier> join = from.join(Product_.supplier, Join.Type.LEFT);
        query.select(from).where(
                like(join.get(Supplier_.name), variable("test"))
        );

        String execute = execute(query);

        String result = "select product.* from Product product left join Supplier supplier on supplier.id = product.supplier_id where supplier.name like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testManyToOne() {
        Query<OrderDetail> query = query(OrderDetail.class);
        From<OrderDetail> from = query.from(OrderDetail.class);

        AbstractJoin<OrderDetail, Product> join = from.join(OrderDetail_.product);

        query.select(from).where(
                like(join.get(Product_.name), variable("test"))
        );

        String execute = execute(query);

        String result = "select orderdetail.* from OrderDetail orderdetail join Product product on product.id = orderdetail.product_id where product.name like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testManyToManyJoin() {
        Query<Product> query = query(Product.class);
        From<Product> from = query.from(Product.class);

        AbstractJoin<Product, Text> join = from.join(Product_.texts);

        query.select(from).where(
                like(join.get(Text_.data), variable("test"))
        );

        String execute = execute(query);

        String result = "select product.* from Product product join Product_Text product_text on product.id = product_text.product_id join Text text on text.id = product_text.texts_id where text.data like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testOneToOneMapped() {
        Query<Supplier> query = query(Supplier.class);
        From<Supplier> from = query.from(Supplier.class);

        AbstractJoin<Supplier, Product> join = from.join(Supplier_.product);

        query.select(from).where(
                like(join.get(Product_.name), variable("test"))
        );

        String execute = execute(query);

        String result = "select supplier.* from Supplier supplier join Product product on supplier.id = product.supplier_id where product.name like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testOneToManyMapped() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        AbstractJoin<Person, Email> join = from.join(Person_.emails);

        query.select(from).where(
                like(join.get(Email_.email), variable("test"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person join Email email on person.id = email.PERSON_ID where email.email like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testManyToManyMapped() {
        Query<Text> query = query(Text.class);
        From<Text> from = query.from(Text.class);

        AbstractJoin<Text, Product> join = from.join(Text_.products);

        query.select(from).where(
                like(join.get(Product_.name), variable("test"))
        );

        String execute = execute(query);

        String result = "select text.* from Text text join Text_Product text_product on text.id = text_product.texts_id join Product product on product.id = text_product.products_id where product.name like :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testCharAndConcat() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                like(concat(from.get(Person_.firstName), aChar(variable(11)), variable("test")), variable("test"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person where person.firstName + char(:v1) + :v2 like :v3";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testConcatWS() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                like(concatWs(".", from.get(Person_.firstName), aChar(variable(11))), variable("test"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person where concat_ws('.', person.firstName, char(:v1)) like :v2";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testDataLength() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                greaterThan(dataLength(from.get(Person_.firstName)), variable(10))
        );

        String execute = execute(query);

        String result = "select person.* from Person person where dataLength(person.firstName) > :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testDifference() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                greaterThan(difference(from.get(Person_.firstName), from.get(Person_.lastName)), variable(2))
        );

        String execute = execute(query);

        String result = "select person.* from Person person where difference(person.firstName, person.lastName) > :v1";

        Assertions.assertEquals(result, execute);
    }

    @Test
    public void testFormat() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                equal(format(from.get(Person_.id), variable("###-###-##")), variable("123-456-78"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person where format(person.id, :v1) = :v2";

        Assertions.assertEquals(result, execute);
    }


    @Test
    public void testLeft() {
        Query<Person> query = query(Person.class);
        From<Person> from = query.from(Person.class);

        query.select(from).where(
                equal(left(from.get(Person_.firstName), variable(3)), variable("Pat"))
        );

        String execute = execute(query);

        String result = "select person.* from Person person where left(person.firstName, :v1) = :v2";

        Assertions.assertEquals(result, execute);
    }

}

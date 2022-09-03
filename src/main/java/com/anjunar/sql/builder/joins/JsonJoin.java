package com.anjunar.sql.builder.joins;

import com.anjunar.sql.builder.Join;
import com.anjunar.sql.builder.From;
import jakarta.persistence.metamodel.SingularAttribute;

public class JsonJoin<E,U> extends Join<E,U> {

    private final String attribute;
    private final String property;

    public JsonJoin(String attribute, String property) {
        super(null);
        this.attribute = attribute;
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    @Override
    public String execute() {
        return new StringBuilder()
                .append("json_array_elements(")
                .append(getParent().getAlias())
                .append(".")
                .append(attribute)
                .append(" -> '")
                .append(property)
                .append("') ")
                .append(property)
                .toString();
    }
}

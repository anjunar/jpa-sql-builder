package com.anjunar.sql.builder.joins;

import com.anjunar.sql.builder.Join;
import com.anjunar.sql.builder.From;
import jakarta.persistence.metamodel.SingularAttribute;

public class JsonJoin<E,U> extends Join<E,U> {

    private final SingularAttribute<E,U> attribute;
    private final String property;

    public JsonJoin(SingularAttribute<E,U> attribute, String property) {
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
                .append(attribute.getName())
                .append(" -> '")
                .append(property)
                .append("') ")
                .append(property)
                .toString();
    }
}

package com.anjunar.sql.builder.joins.postgres;

import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.AbstractJoin;
import jakarta.persistence.metamodel.SingularAttribute;

public class JsonJoin<E,U> extends AbstractJoin<E,U> {

    private final SingularAttribute<E,U> attribute;
    private final String property;

    public JsonJoin(SingularAttribute<E,U> attribute, String property) {
        super(null, null);
        this.attribute = attribute;
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    @Override
    public String execute(Context context) {
        return new StringBuilder()
                .append("json_array_elements(")
                .append(getParent().destinationTableAlias())
                .append(".")
                .append(attribute.getName())
                .append(" -> '")
                .append(property)
                .append("') ")
                .append(property)
                .toString();
    }
}

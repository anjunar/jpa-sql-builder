package com.anjunar.sql.builder.functions.string;

import com.anjunar.sql.builder.AbstractFunction;
import com.anjunar.sql.builder.Context;
import com.anjunar.sql.builder.Expression;

public class FormatFunction extends AbstractFunction<String> {

    private final Expression<? extends Comparable<?>> value;

    private final Expression<String> format;

    private final Expression<String> culture;

    public FormatFunction(Expression<? extends Comparable<?>> value, Expression<String> format, Expression<String> culture) {
        this.value = value;
        this.format = format;
        this.culture = culture;
    }

    @Override
    public String execute(Context context) {
        String result = "format(" +
                value.execute(context) +
                ", " +
                format.execute(context);

        if (culture == null) {
            result += ")";
        } else {
            result += ", " +
                    culture.execute(context) +
                    ")";
        }

        return result;

    }
}

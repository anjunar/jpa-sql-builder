package com.anjunar.sql.builder;

import java.util.HashMap;
import java.util.Map;

public abstract class Predicate {

    private final Map<Integer, Object> mapping = new HashMap<>();

    public Map<Integer, Object> mapping() {
        return mapping;
    }

    public abstract String execute(Context context);

}

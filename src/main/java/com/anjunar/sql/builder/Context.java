package com.anjunar.sql.builder;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private Integer index = 1;

    private final Map<Integer, Object> mappings = new HashMap<>();

    public Integer next() {
        return index++;
    }

    public Map<Integer, Object> mappings() {
        return mappings;
    }

    @Override
    public String toString() {
        return "Context{" +
                "mappings=" + mappings +
                '}';
    }
}

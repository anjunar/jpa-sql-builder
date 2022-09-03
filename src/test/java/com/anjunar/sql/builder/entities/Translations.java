package com.anjunar.sql.builder.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Translations implements Serializable {

    private final Set<Translation> translations = new HashSet<>();

    @JsonCreator
    public Translations(@JsonProperty("translations") Translation... value) {
        translations.addAll(Sets.newHashSet(value));
    }

    public void add(Translation translation) {
        translations.add(translation);
    }
}

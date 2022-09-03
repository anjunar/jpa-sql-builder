package com.anjunar.sql.builder.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Locale;

public class Translation implements Serializable {

    @JsonProperty("text")
    private String text;

    @JsonProperty("locale")
    private Locale locale;

    public Translation(Locale forLanguageTag, String value) {
        text = value;
        locale = forLanguageTag;
    }

    public Translation() {
        this(null, null);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}

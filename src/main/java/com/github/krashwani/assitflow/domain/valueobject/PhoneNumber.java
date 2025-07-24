package com.github.krashwani.assitflow.domain.valueobject;

import java.util.Objects;

public class PhoneNumber {

    private final String value;

    public PhoneNumber(String value) {
        if (!value.matches("\\+\\d{1,3}\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // equals and hashCode based on value
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}


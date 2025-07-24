package com.github.krashwani.assitflow.domain.valueobject;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class EmailAddress {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");
    private final String value;

    public EmailAddress(String value) {
        if (!EMAIL_REGEX.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email address: " + value);
        }
        this.value = value;
    }

    // Equality by value
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailAddress that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}


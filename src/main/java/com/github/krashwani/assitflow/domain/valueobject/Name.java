package com.github.krashwani.assitflow.domain.valueobject;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Name {

    private final String firstName;
    private final String lastName;

    public Name(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("First name required");
        if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Last name required");
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name name)) return false;
        return firstName.equals(name.firstName) && lastName.equals(name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}


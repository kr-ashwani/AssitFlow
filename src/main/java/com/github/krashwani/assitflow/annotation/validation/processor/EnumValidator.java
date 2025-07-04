package com.github.krashwani.assitflow.annotation.validation.processor;

import com.github.krashwani.assitflow.annotation.validation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private Class<? extends Enum<?>> enumClass;
    private boolean includeNull;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumClass = annotation.enumClass();
        this.includeNull = annotation.includeNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return this.includeNull;

        String valueToCheck;

        if (value instanceof Enum<?>) {
            valueToCheck = ((Enum<?>) value).name();
        } else if (value instanceof String) {
            valueToCheck = (String) value;
        } else {
            return false; // unsupported type
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .anyMatch(e -> e.equals(valueToCheck));
    }
}

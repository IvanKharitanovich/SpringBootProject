package com.ivankharytanovich.springbootapp.model.enums;

public enum Status {
    ACTIVE(true), INACTIVE(false);

    private final boolean value;

    private Status(boolean value) {
        this.value = value;
    }

    public boolean booleanValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.name();
    }
}

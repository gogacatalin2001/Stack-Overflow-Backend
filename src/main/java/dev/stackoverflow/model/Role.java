package dev.stackoverflow.model;

public enum Role {
    USER,
    MODERATOR;

    @Override
    public String toString() {
        return this.name();
    }
}

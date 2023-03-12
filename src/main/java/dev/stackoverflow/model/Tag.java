package dev.stackoverflow.model;

import java.util.Objects;

public record Tag(String tag) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag1 = (Tag) o;
        return tag().equals(tag1.tag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag());
    }
}

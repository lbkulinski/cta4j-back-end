package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Objects;

public record Direction(@JsonAlias("dir") String name) {
    public Direction {
        Objects.requireNonNull(name);
    }
}

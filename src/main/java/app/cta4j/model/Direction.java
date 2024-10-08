package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Represents a direction for a route (e.g., Northbound or Southbound).")
public record Direction(
    @Schema(description = "The name of the direction (e.g., Northbound or Southbound).")
    @JsonAlias("dir")
    String name
) {
    public Direction {
        Objects.requireNonNull(name);
    }
}

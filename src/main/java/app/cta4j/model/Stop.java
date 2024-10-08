package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Represents a stop on a route.")
public record Stop(
    @Schema(description = "The unique identifier of the stop.")
    @JsonAlias("stpid")
    int id,

    @Schema(description = "The name of the stop.")
    @JsonAlias("stpnm")
    String name
) {
    public Stop {
        Objects.requireNonNull(name);
    }
}

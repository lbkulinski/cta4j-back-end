package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Represents a bus route and its details.", requiredProperties = {"id", "name"})
@JsonIgnoreProperties(ignoreUnknown = true)
public record Route(
    @Schema(description = "The unique identifier of the route.")
    @JsonAlias("rt")
    String id,

    @Schema(description = "The name of the route.")
    @JsonAlias("rtnm")
    String name
) {
    public Route {
        Objects.requireNonNull(id);

        Objects.requireNonNull(name);
    }
}

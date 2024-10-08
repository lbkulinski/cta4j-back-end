package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Represents a train station and its details.")
@JsonIgnoreProperties(ignoreUnknown = true)
public record Station(
    @Schema(description = "The unique identifier of the station.")
    @JsonAlias("map_id")
    int id,

    @Schema(description = "The name of the station.")
    @JsonAlias("station_descriptive_name")
    String name
) {
    public Station {
        Objects.requireNonNull(name);
    }
}

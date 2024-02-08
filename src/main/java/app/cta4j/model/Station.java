package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Station(
    @JsonAlias("map_id")
    int id,

    @JsonAlias("station_descriptive_name")
    String name
) {
    public Station {
        Objects.requireNonNull(name);
    }
}

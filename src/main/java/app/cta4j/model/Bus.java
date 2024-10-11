package app.cta4j.model;

import app.cta4j.serialization.BusStringToInstantConverter;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Objects;

@Schema(
    description = "Represents a bus and its schedule information.",
    requiredProperties = {"id", "type", "stop", "route", "destination", "predictionTime", "arrivalTime", "delayed"}
)
public record Bus(
    @Schema(description = "The unique identifier of the bus.")
    @JsonAlias("vid")
    int id,

    @Schema(description = "The type of stop event (either arrival or departure).", example = "ARRIVAL")
    @JsonAlias("typ")
    StopEventType type,

    @Schema(description = "The stop where the bus will next arrive or depart.")
    @JsonAlias("stpnm")
    String stop,

    @Schema(description = "The route on which the bus is operating.")
    @JsonAlias("rt")
    String route,

    @Schema(description = "The final destination of the bus.")
    @JsonAlias("des")
    String destination,

    @Schema(
        description = "The time when the arrival or departure prediction was made.",
        format = "date-time",
        example = "2024-10-07T12:34:56Z"
    )
    @JsonAlias("tmstmp")
    @JsonDeserialize(converter = BusStringToInstantConverter.class)
    Instant predictionTime,

    @Schema(
        description = "The estimated arrival or departure time of the bus at the stop.",
        format = "date-time",
        example = "2024-10-07T12:40:00Z"
    )
    @JsonAlias("prdtm")
    @JsonDeserialize(converter = BusStringToInstantConverter.class)
    Instant arrivalTime,

    @Schema(description = "Indicates whether the bus is delayed.")
    @JsonAlias("dly")
    boolean delayed
) {
    public Bus {
        Objects.requireNonNull(type);

        Objects.requireNonNull(stop);

        Objects.requireNonNull(route);

        Objects.requireNonNull(destination);

        Objects.requireNonNull(predictionTime);

        Objects.requireNonNull(arrivalTime);
    }
}

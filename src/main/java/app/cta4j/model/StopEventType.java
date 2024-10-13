package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = """
    Represents the type of stop event, indicating whether the vehicle is arriving at or departing from a stop.""")
public enum StopEventType {
    @Schema(description = "The vehicle is arriving at the stop.")
    ARRIVAL,

    @Schema(description = "The vehicle is departing from the stop.")
    DEPARTURE;

    @JsonCreator
    public static StopEventType ofString(String string) {
        Objects.requireNonNull(string);

        return switch (string) {
            case "A" -> StopEventType.ARRIVAL;
            case "D" -> StopEventType.DEPARTURE;
            default -> {
                String message = "\"%s\" is not a valid type".formatted(string);

                throw new IllegalArgumentException(message);
            }
        };
    }
}

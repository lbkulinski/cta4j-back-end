package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a direction for a route (e.g., Northbound or Southbound).")
public enum Direction {
    @Schema(description = "The bus is traveling northbound on the route.")
    NORTHBOUND,

    @Schema(description = "The bus is traveling southbound on the route.")
    SOUTHBOUND,

    @Schema(description = "The bus is traveling eastbound on the route.")
    EASTBOUND,

    @Schema(description = "The bus is traveling westbound on the route.")
    WESTBOUND;

    @JsonValue
    public String toFormattedString() {
        String firstLetter = this.name()
                                 .substring(0, 1);

        String restOfName = this.name()
                                .substring(1)
                                .toLowerCase();

        return firstLetter + restOfName;
    }
}

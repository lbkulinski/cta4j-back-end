package app.cta4j.model;

import app.cta4j.serialization.StringToBooleanConverter;
import app.cta4j.serialization.TrainStringToInstantConverter;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Objects;

@Schema(description = "Represents a train and its schedule information.")
@JsonIgnoreProperties(ignoreUnknown = true)
public record Train(
    @Schema(description = "The unique run number of the train.")
    @JsonAlias("rn")
    int run,

    @Schema(description = "The line on which the train operates.", nullable = true, example = "RED")
    @JsonAlias("rt")
    Line line,

    @Schema(description = "The final destination of the train.")
    @JsonAlias("destNm")
    String destination,

    @Schema(description = "The station where the train will next arrive.")
    @JsonAlias("staNm")
    String station,

    @Schema(
        description = "The time when the arrival prediction was made.",
        format = "date-time",
        example = "2024-10-08T13:33:05Z"
    )
    @JsonAlias("prdt")
    @JsonDeserialize(converter = TrainStringToInstantConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    Instant predictionTime,

    @Schema(
        description = "The estimated arrival time of the train at the station.",
        format = "date-time",
        example = "2024-10-08T20:27:15Z"
    )
    @JsonAlias("arrT")
    @JsonDeserialize(converter = TrainStringToInstantConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    Instant arrivalTime,

    @Schema(description = "Indicates whether the train is due to arrive soon.")
    @JsonAlias("isApp")
    @JsonDeserialize(converter = StringToBooleanConverter.class)
    boolean due,

    @Schema(description = "Indicates whether the train is arriving according to its schedule.")
    @JsonAlias("isSch")
    @JsonDeserialize(converter = StringToBooleanConverter.class)
    boolean scheduled,

    @Schema(description = "Indicates whether the train is delayed.")
    @JsonAlias("isDly")
    @JsonDeserialize(converter = StringToBooleanConverter.class)
    boolean delayed
) {
    public Train {
        Objects.requireNonNull(destination);

        Objects.requireNonNull(station);

        Objects.requireNonNull(predictionTime);

        Objects.requireNonNull(arrivalTime);
    }
}

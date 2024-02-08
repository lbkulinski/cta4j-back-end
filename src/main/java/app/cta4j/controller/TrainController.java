package app.cta4j.controller;

import app.cta4j.model.Station;
import app.cta4j.model.Train;
import app.cta4j.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

@Tag(name = "Trains", description = "Operations pertaining to trains")
@RestController
@RequestMapping("/api/trains")
public final class TrainController {
    private final TrainService service;

    @Autowired
    public TrainController(TrainService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Operation(summary = "Get all stations")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all stations",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Station.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "An internal server error occurred while retrieving the stations",
            content = @Content
        )
    })
    @GetMapping(value = "/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Station>> getStations() {
        return this.service.getStations();
    }

    @Operation(summary = "Get trains arriving at the specified station")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved trains arriving at the specified station",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Train.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "An internal server error occurred while retrieving the trains",
            content = @Content
        )
    })
    @GetMapping(value = "/stations/{stationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Train>> getStations(@PathVariable int stationId) {
        return this.service.getTrains(stationId);
    }

    @Operation(summary = "Get the upcoming stops for the train with the specified run number")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the upcoming stops for the train with the specified run number",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Train.class))
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "An internal server error occurred while retrieving the upcoming stops",
            content = @Content
        )
    })
    @GetMapping(value = "/follow/{run}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Train>> followTrain(@PathVariable int run) {
        return this.service.followTrain(run);
    }
}

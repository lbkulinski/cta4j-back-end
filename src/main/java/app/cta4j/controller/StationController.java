package app.cta4j.controller;

import app.cta4j.model.Station;
import app.cta4j.model.Train;
import app.cta4j.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

@Tag(name = "Station API", description = """
     Operations for retrieving station data, including upcoming train arrivals at a specific station.""")
@RestController
@RequestMapping("/api/stations")
public final class StationController {
    private final StationService service;

    @Autowired
    public StationController(StationService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Operation(
        summary = "Retrieve the list of train stations.",
        description = "Retrieves the list of all available train stations in the system."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of train stations.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Station.class))
            )
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Set<Station>> getStations() {
        Set<Station> stations = this.service.getStations();

        stations = Set.copyOf(stations);

        return ResponseEntity.ok(stations);
    }

    @Operation(
        summary = "Retrieve upcoming train arrivals for a station.",
        description = """
            Retrieves a list of upcoming train arrivals for a specific station, identified by the station ID."""
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of upcoming train arrivals.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Train.class))
            )
        ),
        @ApiResponse(responseCode = "404", description = "Station not found.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{stationId}/arrivals")
    public ResponseEntity<Set<Train>> getArrivals(
        @Parameter(
            in = ParameterIn.PATH,
            required = true,
            description = "The unique ID of the station.",
            schema = @Schema(type = "integer", format = "int32")
        )
        @PathVariable
        int stationId
    ) {
        Set<Train> stations = this.service.getArrivals(stationId);

        stations = Set.copyOf(stations);

        return ResponseEntity.ok(stations);
    }
}

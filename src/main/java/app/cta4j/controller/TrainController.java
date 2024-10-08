package app.cta4j.controller;

import app.cta4j.model.Bus;
import app.cta4j.model.Train;
import app.cta4j.service.TrainService;
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

@Tag(name = "Train API", description = """
    Operations for retrieving train data, including upcoming stations for a specific train run.""")
@RestController
@RequestMapping("/api/trains")
public final class TrainController {
    private final TrainService service;

    @Autowired
    public TrainController(TrainService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Operation(
        summary = "Retrieve upcoming stations for a train run.",
        description = "Retrieves a list of upcoming stations for a specific train, identified by its run number."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of upcoming stations.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Bus.class))
            )
        ),
        @ApiResponse(responseCode = "404", description = "Train run not found.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{run}/stations")
    public ResponseEntity<Set<Train>> getUpcomingStations(
        @Parameter(
            in = ParameterIn.PATH,
            required = true,
            description = "The unique run number of the train.",
            schema = @Schema(type = "integer", format = "int32")
        )
        @PathVariable
        int run
    ) {
        Set<Train> stations = this.service.getUpcomingStations(run);

        stations = Set.copyOf(stations);

        return ResponseEntity.ok(stations);
    }
}

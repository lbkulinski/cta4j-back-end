package app.cta4j.controller;

import app.cta4j.model.Bus;
import app.cta4j.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

@Tag(name = "Bus API", description = """
     Operations for retrieving bus data, including upcoming stops for a specific bus.""")
@RestController
@RequestMapping("/api/buses")
public final class BusController {
    private final BusService service;

    public BusController(BusService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Operation(
        summary = "Retrieve upcoming stops for a bus.",
        description = "Retrieves a list of upcoming stops for a specific bus, identified by its ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of upcoming stops.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Bus.class))
            )
        ),
        @ApiResponse(responseCode = "404", description = "Bus not found.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{id}/stops")
    public ResponseEntity<Set<Bus>> getUpcomingStops(
        @Parameter(
            in = ParameterIn.PATH,
            required = true,
            description = "The unique ID of the bus.",
            schema = @Schema(type = "integer", format = "int32")
        )
        @PathVariable
        int id
    ) {
        Set<Bus> stops = this.service.getUpcomingStops(id);

        stops = Set.copyOf(stops);

        return ResponseEntity.ok(stops);
    }
}

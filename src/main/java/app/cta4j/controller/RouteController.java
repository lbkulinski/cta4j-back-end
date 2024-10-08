package app.cta4j.controller;

import app.cta4j.model.*;
import app.cta4j.service.RouteService;
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

@Tag(name = "Route API", description = """
     Operations for retrieving route data, including stops and directions for a specific route.""")
@RestController
@RequestMapping("/api/routes")
public final class RouteController {
    private final RouteService service;

    @Autowired
    public RouteController(RouteService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Operation(
        summary = "Retrieve the list of routes.",
        description = "Retrieves the list of all available routes in the system."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of routes.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Route.class))
            )
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Set<Route>> getRoutes() {
        Set<Route> routes = this.service.getRoutes();

        routes = Set.copyOf(routes);

        return ResponseEntity.ok(routes);
    }

    @Operation(
        summary = " Retrieve directions for a route.",
        description = """
            Retrieves a list of directions (e.g., Northbound or Southbound) for a specific route, identified by the \
            route ID."""
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of directions for the route.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Direction.class))
            )
        ),
        @ApiResponse(responseCode = "404", description = "Route not found.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{routeId}/directions")
    public ResponseEntity<Set<Direction>> getDirections(
        @Parameter(
            in = ParameterIn.PATH,
            required = true,
            description = "The unique ID of the route.",
            schema = @Schema(type = "string")
        )
        @PathVariable
        String routeId
    ) {
        Set<Direction> directions = this.service.getDirections(routeId);

        directions = Set.copyOf(directions);

        return ResponseEntity.ok(directions);
    }

    @Operation(
        summary = "Retrieve stops for a route and direction.",
        description = """
            Retrieves a list of stops for a specific route and direction, identified by the route ID and direction."""
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of stops for the route and direction.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Stop.class))
            )
        ),
        @ApiResponse(responseCode = "404", description = "Route or direction not found.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{routeId}/directions/{direction}/stops")
    public ResponseEntity<Set<Stop>> getStops(
        @Parameter(
            in = ParameterIn.PATH,
            required = true,
            description = "The unique ID of the route.",
            schema = @Schema(type = "string")
        )
        @PathVariable String routeId,

        @Parameter(
            in = ParameterIn.PATH,
            required = true,
            description = "The direction of the route (e.g., Northbound or Southbound).",
            schema = @Schema(type = "string")
        )
        @PathVariable String direction
    ) {
        Set<Stop> stops = this.service.getStops(routeId, direction);

        stops = Set.copyOf(stops);

        return ResponseEntity.ok(stops);
    }

    @Operation(
        summary = "Retrieve upcoming bus arrivals for a stop on a route.",
        description = """
            Retrieves a list of upcoming bus arrivals for a specific stop on a route, identified by the route ID and \
            stop ID."""
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of upcoming bus arrivals.",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Bus.class))
            )
        ),
        @ApiResponse(responseCode = "404", description = "Route or stop not found.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @GetMapping("/{routeId}/stops/{stopId}/arrivals")
    public ResponseEntity<Set<Bus>> getArrivals(
        @Parameter(
            in = ParameterIn.PATH,
            required = true,
            description = "The unique ID of the route.",
            schema = @Schema(type = "string")
        )
        @PathVariable String routeId,

        @Parameter(
            in = ParameterIn.PATH,
            required = true,
            description = "The unique ID of the stop.",
            schema = @Schema(type = "integer", format = "int32")
        )
        @PathVariable int stopId
    ) {
        Set<Bus> buses = this.service.getArrivals(routeId, stopId);

        buses = Set.copyOf(buses);

        return ResponseEntity.ok(buses);
    }
}

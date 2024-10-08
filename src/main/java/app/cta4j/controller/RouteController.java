package app.cta4j.controller;

import app.cta4j.model.Bus;
import app.cta4j.model.Direction;
import app.cta4j.model.Route;
import app.cta4j.model.Stop;
import app.cta4j.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/routes")
public final class RouteController {
    private final RouteService service;

    @Autowired
    public RouteController(RouteService service) {
        this.service = Objects.requireNonNull(service);
    }

    @GetMapping
    public ResponseEntity<Set<Route>> getRoutes() {
        Set<Route> routes = this.service.getRoutes();

        routes = Set.copyOf(routes);

        return ResponseEntity.ok(routes);
    }

    @GetMapping("/{routeId}/directions")
    public ResponseEntity<Set<Direction>> getDirections(@PathVariable String routeId) {
        Set<Direction> directions = this.service.getDirections(routeId);

        directions = Set.copyOf(directions);

        return ResponseEntity.ok(directions);
    }

    @GetMapping("/{routeId}/directions/{direction}/stops")
    public ResponseEntity<Set<Stop>> getStops(@PathVariable String routeId, @PathVariable String direction) {
        Set<Stop> stops = this.service.getStops(routeId, direction);

        stops = Set.copyOf(stops);

        return ResponseEntity.ok(stops);
    }

    @GetMapping("/{routeId}/stops/{stopId}/arrivals")
    public ResponseEntity<Set<Bus>> getArrivals(@PathVariable String routeId, @PathVariable int stopId) {
        Set<Bus> buses = this.service.getArrivals(routeId, stopId);

        buses = Set.copyOf(buses);

        return ResponseEntity.ok(buses);
    }
}

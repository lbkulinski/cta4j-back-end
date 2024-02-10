package app.cta4j.controller;

import app.cta4j.model.Bus;
import app.cta4j.model.Direction;
import app.cta4j.model.Route;
import app.cta4j.model.Stop;
import app.cta4j.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.Set;

@Controller
public final class BusController {
    private final BusService service;

    @Autowired
    public BusController(BusService service) {
        Objects.requireNonNull(service);

        this.service = service;
    }

    @QueryMapping
    public Set<Route> getRoutes() {
        return this.service.getRoutes();
    }

    @QueryMapping
    public Set<Direction> getRouteDirections(@Argument String id) {
        Objects.requireNonNull(id);

        return this.service.getRouteDirections(id);
    }

    @QueryMapping
    public Set<Stop> getRouteStops(@Argument String id, @Argument String direction) {
        Objects.requireNonNull(id);

        Objects.requireNonNull(direction);

        return this.service.getRouteStops(id, direction);
    }

    @QueryMapping
    public Set<Bus> getBuses(@Argument String routeId, @Argument int stopId) {
        Objects.requireNonNull(routeId);

        return this.service.getBuses(routeId, stopId);
    }

    @QueryMapping
    public Set<Bus> followBus(@Argument int id) {
        return this.service.followBus(id);
    }
}

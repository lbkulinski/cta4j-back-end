package app.cta4j.service;

import app.cta4j.client.BusClient;
import app.cta4j.exception.ResourceNotFoundException;
import app.cta4j.jooq.Tables;
import app.cta4j.model.*;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public final class RouteService {
    private final DSLContext context;

    private final BusClient client;

    @Autowired
    public RouteService(DSLContext context, BusClient client) {
        this.context = Objects.requireNonNull(context);

        this.client = Objects.requireNonNull(client);
    }

    public Set<Route> getRoutes() {
        List<Route> routes = this.context.select(Tables.ROUTE.asterisk())
                                         .from(Tables.ROUTE)
                                         .fetchInto(Route.class);

        return Set.copyOf(routes);
    }

    public Set<Direction> getDirections(String routeId) {
        List<Direction> directions = this.context.select(DSL.upper(Tables.DIRECTION.NAME))
                                                 .from(Tables.ROUTE_DIRECTION)
                                                 .join(Tables.DIRECTION)
                                                 .on(Tables.DIRECTION.ID.eq(Tables.ROUTE_DIRECTION.DIRECTION_ID))
                                                 .where(Tables.ROUTE_DIRECTION.ROUTE_ID.eq(routeId))
                                                 .fetchInto(Direction.class);

        return Set.copyOf(directions);
    }

    public Set<Stop> getStops(String routeId, String direction) {
        List<Stop> stops = this.context.select(Tables.ROUTE_STOP.asterisk())
                                       .from(Tables.ROUTE_STOP)
                                       .join(Tables.DIRECTION)
                                       .on(Tables.DIRECTION.ID.eq(Tables.ROUTE_STOP.DIRECTION_ID))
                                       .join(Tables.STOP)
                                       .on(Tables.STOP.ID.eq(Tables.ROUTE_STOP.STOP_ID))
                                       .where(Tables.ROUTE_STOP.ROUTE_ID.eq(routeId))
                                       .and(Tables.DIRECTION.NAME.eq(direction))
                                       .fetchInto(Stop.class);

        return Set.copyOf(stops);
    }

    public Set<Bus> getArrivals(String routeId, int stopId) {
        String stopIdString = Integer.toString(stopId);

        BusResponse response = this.client.getBuses(routeId, stopIdString);

        if (response == null) {
            throw new IllegalStateException("""
                The bus response is null for route ID %s and stop ID %d""".formatted(routeId, stopId));
        }

        BusBody body = response.body();

        if (body == null) {
            throw new ResourceNotFoundException("""
                The bus body is null for route ID %s and stop ID %d""".formatted(routeId, stopId));
        }

        Set<Bus> buses = body.buses();

        if (buses == null) {
            throw new ResourceNotFoundException("""
                The Set of buses is null for route ID %s and stop ID %d""".formatted(routeId, stopId));
        }

        return Set.copyOf(buses);
    }
}

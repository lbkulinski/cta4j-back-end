package app.cta4j.service;

import app.cta4j.client.BusClient;
import app.cta4j.exception.DataFetcherException;
import app.cta4j.jooq.Tables;
import app.cta4j.model.*;
import com.rollbar.notifier.Rollbar;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public final class BusService {
    private final DSLContext context;

    private final BusClient client;

    private final Rollbar rollbar;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(BusService.class);
    }

    @Autowired
    public BusService(DSLContext context, BusClient client, Rollbar rollbar) {
        Objects.requireNonNull(context);

        Objects.requireNonNull(client);

        Objects.requireNonNull(rollbar);

        this.context = context;

        this.client = client;

        this.rollbar = rollbar;
    }

    public Set<Route> getRoutes() {
        List<Route> routes;

        try {
            routes = this.context.select(Tables.ROUTE.ID, Tables.ROUTE.NAME)
                                 .from(Tables.ROUTE)
                                 .fetchInto(Route.class);
        } catch (DataAccessException e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            BusService.LOGGER.error(message, e);

            throw new DataFetcherException(ErrorType.INTERNAL_ERROR);
        }

        return Set.copyOf(routes);
    }

    public Set<Direction> getRouteDirections(String id) {
        Objects.requireNonNull(id);

        List<Direction> directions;

        try {
            directions = this.context.select(Tables.DIRECTION.NAME)
                                     .from(Tables.ROUTE_DIRECTION)
                                     .join(Tables.DIRECTION)
                                     .on(Tables.DIRECTION.ID.eq(Tables.ROUTE_DIRECTION.DIRECTION_ID))
                                     .where(Tables.ROUTE_DIRECTION.ROUTE_ID.eq(id))
                                     .fetchInto(Direction.class);
        } catch (DataAccessException e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            BusService.LOGGER.error(message, e);

            throw new DataFetcherException(ErrorType.INTERNAL_ERROR);
        }

        return Set.copyOf(directions);
    }

    public Set<Stop> getRouteStops(String id, String direction) {
        Objects.requireNonNull(id);

        Objects.requireNonNull(direction);

        List<Stop> stops;

        try {
            stops = this.context.select(Tables.STOP.ID, Tables.STOP.NAME)
                                .from(Tables.ROUTE_STOP)
                                .join(Tables.DIRECTION)
                                .on(Tables.DIRECTION.ID.eq(Tables.ROUTE_STOP.DIRECTION_ID))
                                .join(Tables.STOP)
                                .on(Tables.STOP.ID.eq(Tables.ROUTE_STOP.STOP_ID))
                                .where(Tables.ROUTE_STOP.ROUTE_ID.eq(id))
                                .and(Tables.DIRECTION.NAME.eq(direction))
                                .fetchInto(Stop.class);
        } catch (DataAccessException e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            BusService.LOGGER.error(message, e);

            throw new DataFetcherException(ErrorType.INTERNAL_ERROR);
        }

        return Set.copyOf(stops);
    }

    public Set<Bus> getBuses(String routeId, int stopId) {
        Objects.requireNonNull(routeId);

        if (stopId <= 0) {
            String message = "The specified stop ID must be positive integer";

            throw new DataFetcherException(message, ErrorType.BAD_REQUEST);
        }

        BusResponse response;

        try {
            response = this.client.getBuses(routeId, stopId);
        } catch (Exception e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            BusService.LOGGER.error(message, e);

            throw new DataFetcherException(ErrorType.INTERNAL_ERROR);
        }

        if (response == null) {
            throw new DataFetcherException(ErrorType.INTERNAL_ERROR);
        }

        BusBody body = response.body();

        if (body == null) {
            throw new DataFetcherException(ErrorType.INTERNAL_ERROR);
        }

        Set<Bus> buses = body.buses();

        if (buses == null) {
            String message = "Buses with the specified route ID and stop ID could not be found";

            throw new DataFetcherException(message, ErrorType.NOT_FOUND);
        }

        return buses;
    }

    public Set<Bus> followBus(int id) {
        if (id <= 0) {
            String message = "The specified ID must be positive integer";

            throw new DataFetcherException(message, ErrorType.BAD_REQUEST);
        }

        BusResponse response;

        try {
            response = this.client.followBus(id);
        } catch (Exception e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            BusService.LOGGER.error(message, e);

            throw new DataFetcherException(ErrorType.INTERNAL_ERROR);
        }

        if (response == null) {
            throw new DataFetcherException(ErrorType.INTERNAL_ERROR);
        }

        BusBody body = response.body();

        if (body == null) {
            throw new DataFetcherException(ErrorType.INTERNAL_ERROR);
        }

        Set<Bus> buses = body.buses();

        if (buses == null) {
            String message = "Buses with the specified ID could not be found";

            throw new DataFetcherException(message, ErrorType.NOT_FOUND);
        }

        return buses;
    }
}

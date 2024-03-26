package app.cta4j.fetcher;

import app.cta4j.jooq.Tables;
import app.cta4j.model.Direction;
import app.cta4j.model.Route;
import app.cta4j.model.Stop;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.rollbar.notifier.Rollbar;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@DgsComponent
public final class RoutesFetcher {
    private final DSLContext context;

    private final Rollbar rollbar;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(RoutesFetcher.class);
    }

    @Autowired
    public RoutesFetcher(DSLContext context, Rollbar rollbar) {
        this.context = Objects.requireNonNull(context);

        this.rollbar = Objects.requireNonNull(rollbar);
    }

    @DgsQuery
    public List<Route> routes() {
        List<Route> routes;

        try {
            routes = this.context.select(Tables.ROUTE.ID, Tables.ROUTE.NAME)
                                 .from(Tables.ROUTE)
                                 .fetchInto(Route.class);
        } catch (DataAccessException e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            RoutesFetcher.LOGGER.error(message, e);

            throw new RuntimeException(e);
        }

        return List.copyOf(routes);
    }

    @DgsQuery
    public List<Direction> routeDirections(@InputArgument String id) {
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

            RoutesFetcher.LOGGER.error(message, e);

            throw new RuntimeException(e);
        }

        return List.copyOf(directions);
    }

    @DgsQuery
    public List<Stop> routeStops(@InputArgument String id, @InputArgument String direction) {
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

            RoutesFetcher.LOGGER.error(message, e);

            throw new RuntimeException(e);
        }

        return List.copyOf(stops);
    }
}

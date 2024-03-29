/*
 * This file is generated by jOOQ.
 */
package app.cta4j.jooq;


import app.cta4j.jooq.tables.Direction;
import app.cta4j.jooq.tables.Route;
import app.cta4j.jooq.tables.RouteDirection;
import app.cta4j.jooq.tables.RouteStop;
import app.cta4j.jooq.tables.Station;
import app.cta4j.jooq.tables.Stop;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.direction</code>.
     */
    public final Direction DIRECTION = Direction.DIRECTION;

    /**
     * The table <code>public.route</code>.
     */
    public final Route ROUTE = Route.ROUTE;

    /**
     * The table <code>public.route_direction</code>.
     */
    public final RouteDirection ROUTE_DIRECTION = RouteDirection.ROUTE_DIRECTION;

    /**
     * The table <code>public.route_stop</code>.
     */
    public final RouteStop ROUTE_STOP = RouteStop.ROUTE_STOP;

    /**
     * The table <code>public.station</code>.
     */
    public final Station STATION = Station.STATION;

    /**
     * The table <code>public.stop</code>.
     */
    public final Stop STOP = Stop.STOP;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Direction.DIRECTION,
            Route.ROUTE,
            RouteDirection.ROUTE_DIRECTION,
            RouteStop.ROUTE_STOP,
            Station.STATION,
            Stop.STOP
        );
    }
}

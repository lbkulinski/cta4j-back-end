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
import app.cta4j.jooq.tables.records.DirectionRecord;
import app.cta4j.jooq.tables.records.RouteDirectionRecord;
import app.cta4j.jooq.tables.records.RouteRecord;
import app.cta4j.jooq.tables.records.RouteStopRecord;
import app.cta4j.jooq.tables.records.StationRecord;
import app.cta4j.jooq.tables.records.StopRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * cta4j.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<DirectionRecord> KEY_DIRECTION_PRIMARY = Internal.createUniqueKey(Direction.DIRECTION, DSL.name("KEY_direction_PRIMARY"), new TableField[] { Direction.DIRECTION.ID }, true);
    public static final UniqueKey<RouteRecord> KEY_ROUTE_PRIMARY = Internal.createUniqueKey(Route.ROUTE, DSL.name("KEY_route_PRIMARY"), new TableField[] { Route.ROUTE.ID }, true);
    public static final UniqueKey<RouteDirectionRecord> KEY_ROUTE_DIRECTION_PRIMARY = Internal.createUniqueKey(RouteDirection.ROUTE_DIRECTION, DSL.name("KEY_route_direction_PRIMARY"), new TableField[] { RouteDirection.ROUTE_DIRECTION.ROUTE_ID, RouteDirection.ROUTE_DIRECTION.DIRECTION_ID }, true);
    public static final UniqueKey<RouteStopRecord> KEY_ROUTE_STOP_PRIMARY = Internal.createUniqueKey(RouteStop.ROUTE_STOP, DSL.name("KEY_route_stop_PRIMARY"), new TableField[] { RouteStop.ROUTE_STOP.ROUTE_ID, RouteStop.ROUTE_STOP.DIRECTION_ID, RouteStop.ROUTE_STOP.STOP_ID }, true);
    public static final UniqueKey<StationRecord> KEY_STATION_PRIMARY = Internal.createUniqueKey(Station.STATION, DSL.name("KEY_station_PRIMARY"), new TableField[] { Station.STATION.ID }, true);
    public static final UniqueKey<StopRecord> KEY_STOP_PRIMARY = Internal.createUniqueKey(Stop.STOP, DSL.name("KEY_stop_PRIMARY"), new TableField[] { Stop.STOP.ID }, true);
}

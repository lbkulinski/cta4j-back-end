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

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<DirectionRecord> DIRECTION_PKEY = Internal.createUniqueKey(Direction.DIRECTION, DSL.name("direction_pkey"), new TableField[] { Direction.DIRECTION.ID }, true);
    public static final UniqueKey<RouteRecord> ROUTE_PKEY = Internal.createUniqueKey(Route.ROUTE, DSL.name("route_pkey"), new TableField[] { Route.ROUTE.ID }, true);
    public static final UniqueKey<RouteDirectionRecord> ROUTE_DIRECTION_PKEY = Internal.createUniqueKey(RouteDirection.ROUTE_DIRECTION, DSL.name("route_direction_pkey"), new TableField[] { RouteDirection.ROUTE_DIRECTION.ROUTE_ID, RouteDirection.ROUTE_DIRECTION.DIRECTION_ID }, true);
    public static final UniqueKey<RouteStopRecord> ROUTE_STOP_PKEY = Internal.createUniqueKey(RouteStop.ROUTE_STOP, DSL.name("route_stop_pkey"), new TableField[] { RouteStop.ROUTE_STOP.ROUTE_ID, RouteStop.ROUTE_STOP.DIRECTION_ID, RouteStop.ROUTE_STOP.STOP_ID }, true);
    public static final UniqueKey<StationRecord> STATION_PKEY = Internal.createUniqueKey(Station.STATION, DSL.name("station_pkey"), new TableField[] { Station.STATION.ID }, true);
    public static final UniqueKey<StopRecord> STOP_PKEY = Internal.createUniqueKey(Stop.STOP, DSL.name("stop_pkey"), new TableField[] { Stop.STOP.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<RouteDirectionRecord, DirectionRecord> ROUTE_DIRECTION__ROUTE_DIRECTION_DIRECTION_ID_FKEY = Internal.createForeignKey(RouteDirection.ROUTE_DIRECTION, DSL.name("route_direction_direction_id_fkey"), new TableField[] { RouteDirection.ROUTE_DIRECTION.DIRECTION_ID }, Keys.DIRECTION_PKEY, new TableField[] { Direction.DIRECTION.ID }, true);
    public static final ForeignKey<RouteDirectionRecord, RouteRecord> ROUTE_DIRECTION__ROUTE_DIRECTION_ROUTE_ID_FKEY = Internal.createForeignKey(RouteDirection.ROUTE_DIRECTION, DSL.name("route_direction_route_id_fkey"), new TableField[] { RouteDirection.ROUTE_DIRECTION.ROUTE_ID }, Keys.ROUTE_PKEY, new TableField[] { Route.ROUTE.ID }, true);
    public static final ForeignKey<RouteStopRecord, DirectionRecord> ROUTE_STOP__ROUTE_STOP_DIRECTION_ID_FKEY = Internal.createForeignKey(RouteStop.ROUTE_STOP, DSL.name("route_stop_direction_id_fkey"), new TableField[] { RouteStop.ROUTE_STOP.DIRECTION_ID }, Keys.DIRECTION_PKEY, new TableField[] { Direction.DIRECTION.ID }, true);
    public static final ForeignKey<RouteStopRecord, RouteRecord> ROUTE_STOP__ROUTE_STOP_ROUTE_ID_FKEY = Internal.createForeignKey(RouteStop.ROUTE_STOP, DSL.name("route_stop_route_id_fkey"), new TableField[] { RouteStop.ROUTE_STOP.ROUTE_ID }, Keys.ROUTE_PKEY, new TableField[] { Route.ROUTE.ID }, true);
    public static final ForeignKey<RouteStopRecord, StopRecord> ROUTE_STOP__ROUTE_STOP_STOP_ID_FKEY = Internal.createForeignKey(RouteStop.ROUTE_STOP, DSL.name("route_stop_stop_id_fkey"), new TableField[] { RouteStop.ROUTE_STOP.STOP_ID }, Keys.STOP_PKEY, new TableField[] { Stop.STOP.ID }, true);
}

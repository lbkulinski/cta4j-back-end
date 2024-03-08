/*
 * This file is generated by jOOQ.
 */
package app.cta4j.jooq.tables;


import app.cta4j.jooq.Keys;
import app.cta4j.jooq.Public;
import app.cta4j.jooq.tables.records.StationRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Station extends TableImpl<StationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.station</code>
     */
    public static final Station STATION = new Station();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StationRecord> getRecordType() {
        return StationRecord.class;
    }

    /**
     * The column <code>public.station.id</code>.
     */
    public final TableField<StationRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.station.name</code>.
     */
    public final TableField<StationRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "");

    private Station(Name alias, Table<StationRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Station(Name alias, Table<StationRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.station</code> table reference
     */
    public Station(String alias) {
        this(DSL.name(alias), STATION);
    }

    /**
     * Create an aliased <code>public.station</code> table reference
     */
    public Station(Name alias) {
        this(alias, STATION);
    }

    /**
     * Create a <code>public.station</code> table reference
     */
    public Station() {
        this(DSL.name("station"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<StationRecord> getPrimaryKey() {
        return Keys.STATION_PKEY;
    }

    @Override
    public Station as(String alias) {
        return new Station(DSL.name(alias), this);
    }

    @Override
    public Station as(Name alias) {
        return new Station(alias, this);
    }

    @Override
    public Station as(Table<?> alias) {
        return new Station(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Station rename(String name) {
        return new Station(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Station rename(Name name) {
        return new Station(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Station rename(Table<?> name) {
        return new Station(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Station where(Condition condition) {
        return new Station(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Station where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Station where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Station where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Station where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Station where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Station where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Station where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Station whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Station whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}

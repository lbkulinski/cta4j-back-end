package app.cta4j.fetcher;

import app.cta4j.jooq.Tables;
import app.cta4j.model.Station;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.rollbar.notifier.Rollbar;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@DgsComponent
public final class StationsFetcher {
    private final DSLContext context;

    private final Rollbar rollbar;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(StationsFetcher.class);
    }

    @Autowired
    public StationsFetcher(DSLContext context, Rollbar rollbar) {
        this.context = Objects.requireNonNull(context);

        this.rollbar = Objects.requireNonNull(rollbar);
    }

    @DgsQuery
    public List<Station> getStations() {
        List<Station> stations;

        try {
            stations = this.context.select(Tables.STATION.ID, Tables.STATION.NAME)
                                   .from(Tables.STATION)
                                   .fetchInto(Station.class);
        } catch (DataAccessException e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            StationsFetcher.LOGGER.error(message, e);

            throw new RuntimeException(e);
        }

        return List.copyOf(stations);
    }
}

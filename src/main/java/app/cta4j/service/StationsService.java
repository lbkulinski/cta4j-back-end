package app.cta4j.service;

import app.cta4j.jooq.Tables;
import app.cta4j.model.Station;
import com.rollbar.notifier.Rollbar;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public final class StationsService {
    private final DSLContext context;

    private final Rollbar rollbar;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(StationsService.class);
    }

    @Autowired
    public StationsService(DSLContext context, Rollbar rollbar) {
        this.context = Objects.requireNonNull(context);

        this.rollbar = Objects.requireNonNull(rollbar);
    }

    public Set<Station> getStations() {
        List<Station> stations = this.context.select()
                                             .from(Tables.STATION)
                                             .fetchInto(Station.class);

        return Set.copyOf(stations);
    }
}

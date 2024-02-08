package app.cta4j.service;

import app.cta4j.client.TrainClient;
import app.cta4j.jooq.Tables;
import app.cta4j.model.*;
import com.rollbar.notifier.Rollbar;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public final class TrainService {
    private final DSLContext context;

    private final TrainClient trainClient;

    private final Rollbar rollbar;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(TrainService.class);
    }

    @Autowired
    public TrainService(DSLContext context, TrainClient trainClient, Rollbar rollbar) {
        this.context = Objects.requireNonNull(context);

        this.trainClient = Objects.requireNonNull(trainClient);

        this.rollbar = Objects.requireNonNull(rollbar);
    }

    public ResponseEntity<Set<Station>> getStations() {
        List<Station> stations;

        try {
            stations = this.context.select(Tables.STATION.ID, Tables.STATION.NAME)
                                   .from(Tables.STATION)
                                   .fetchInto(Station.class);
        } catch (DataAccessException e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            TrainService.LOGGER.error(message, e);

            return ResponseEntity.internalServerError()
                                 .build();
        }

        Set<Station> copy = Set.copyOf(stations);

        return ResponseEntity.ok(copy);
    }

    public ResponseEntity<Set<Train>> getTrains(int stationId) {
        if (stationId <= 0) {
            return ResponseEntity.badRequest()
                                 .build();
        }

        TrainResponse response;

        try {
            response = this.trainClient.getTrains(stationId);
        } catch (Exception e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            TrainService.LOGGER.error(message, e);

            return ResponseEntity.internalServerError()
                                 .build();
        }

        if (response == null) {
            return ResponseEntity.internalServerError()
                                 .build();
        }

        TrainBody body = response.body();

        if (body == null) {
            return ResponseEntity.internalServerError()
                                 .build();
        }

        Set<Train> trains = body.trains();

        if (trains == null) {
            return ResponseEntity.notFound()
                                 .build();
        }

        Set<Train> copy = Set.copyOf(trains);

        return ResponseEntity.ok(copy);
    }

    public ResponseEntity<Set<Train>> followTrain(int run) {
        if (run <= 0) {
            return ResponseEntity.badRequest()
                                 .build();
        }

        FollowResponse response;

        try {
            response = this.trainClient.followTrain(run);
        } catch (Exception e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            TrainService.LOGGER.error(message, e);

            return ResponseEntity.internalServerError()
                                 .build();
        }

        if (response == null) {
            return ResponseEntity.internalServerError()
                                 .build();
        }

        FollowBody body = response.body();

        if (body == null) {
            return ResponseEntity.internalServerError()
                                 .build();
        }

        Set<Train> trains = body.trains();

        if (trains == null) {
            return ResponseEntity.notFound()
                                 .build();
        }

        Set<Train> copy = Set.copyOf(trains);

        return ResponseEntity.ok(copy);
    }
}

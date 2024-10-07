package app.cta4j.service;

import app.cta4j.client.TrainClient;
import app.cta4j.model.Train;
import app.cta4j.model.TrainBody;
import app.cta4j.model.TrainResponse;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.rollbar.notifier.Rollbar;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class TrainsService {
    private final TrainClient client;

    private Rollbar rollbar;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(TrainsService.class);
    }

    public TrainsService(TrainClient client, Rollbar rollbar) {
        this.client = Objects.requireNonNull(client);

        this.rollbar = Objects.requireNonNull(rollbar);
    }

    public Set<Train> getTrains(int stationId) {
        String stationIdString = Integer.toString(stationId);

        TrainResponse response = client.getTrains(stationIdString);

        if (response == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        TrainBody body = response.body();

        if (body == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Set<Train> trains = body.trains();

        if (trains == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return trains.stream()
                     .filter(train -> train.line() != null)
                     .collect(Collectors.toSet());
    }
}

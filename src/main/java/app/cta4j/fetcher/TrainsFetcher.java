package app.cta4j.fetcher;

import app.cta4j.client.TrainClient;
import app.cta4j.model.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.netflix.graphql.dgs.exceptions.DgsInvalidInputArgumentException;
import com.rollbar.notifier.Rollbar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@DgsComponent
public final class TrainsFetcher {
    private final TrainClient client;

    private final Rollbar rollbar;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(TrainsFetcher.class);
    }

    @Autowired
    public TrainsFetcher(TrainClient client, Rollbar rollbar) {
        this.client = Objects.requireNonNull(client);

        this.rollbar = Objects.requireNonNull(rollbar);
    }

    @DgsQuery
    public List<Train> getTrains(@InputArgument String stationId) {
        Objects.requireNonNull(stationId);

        TrainResponse response;

        try {
            response = this.client.getTrains(stationId);
        } catch (Exception e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            TrainsFetcher.LOGGER.error(message, e);

            throw new RuntimeException(e);
        }

        if (response == null) {
            throw new RuntimeException();
        }

        TrainBody body = response.body();

        if (body == null) {
            throw new DgsEntityNotFoundException();
        }

        List<Train> trains = body.trains();

        if (trains == null) {
            throw new DgsEntityNotFoundException();
        }

        return List.copyOf(trains);
    }

    @DgsQuery
    public List<Train> followTrain(@InputArgument Integer run) {
        if (run <= 0) {
            String message = "The specified run number is less than or equal to zero";

            throw new DgsInvalidInputArgumentException(message, null);
        }

        FollowResponse response;

        try {
            response = this.client.followTrain(run);
        } catch (Exception e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            TrainsFetcher.LOGGER.error(message, e);

            throw new RuntimeException(e);
        }

        if (response == null) {
            throw new RuntimeException();
        }

        FollowBody body = response.body();

        if (body == null) {
            throw new DgsEntityNotFoundException();
        }

        List<Train> trains = body.trains();

        if (trains == null) {
            throw new DgsEntityNotFoundException();
        }

        return List.copyOf(trains);
    }
}

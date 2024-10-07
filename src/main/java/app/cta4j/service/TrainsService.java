package app.cta4j.service;

import app.cta4j.client.TrainClient;
import app.cta4j.exception.ResourceNotFoundException;
import app.cta4j.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class TrainsService {
    private final TrainClient client;

    @Autowired
    public TrainsService(TrainClient client) {
        this.client = Objects.requireNonNull(client);
    }

    public Set<Train> getUpcomingStations(int run) {
        FollowResponse response = this.client.getTrain(run);

        if (response == null) {
            throw new IllegalStateException("The follow response is null for run %d".formatted(run));
        }

        FollowBody body = response.body();

        if (body == null) {
            throw new ResourceNotFoundException("The follow body is null for run %d".formatted(run));
        }

        Set<Train> trains = body.trains();

        if (trains == null) {
            throw new ResourceNotFoundException("The Set of trains is null for run %d".formatted(run));
        }

        return trains.stream()
                     .filter(train -> train.line() != null)
                     .collect(Collectors.toSet());
    }
}

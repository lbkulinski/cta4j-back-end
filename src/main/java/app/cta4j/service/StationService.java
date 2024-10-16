package app.cta4j.service;

import app.cta4j.client.TrainClient;
import app.cta4j.exception.ResourceNotFoundException;
import app.cta4j.jooq.Tables;
import app.cta4j.model.*;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StationService {
    private final DSLContext context;

    private final TrainClient client;

    @Autowired
    public StationService(DSLContext context, TrainClient client) {
        this.context = Objects.requireNonNull(context);

        this.client = Objects.requireNonNull(client);
    }

    @Cacheable("stations")
    public Set<Station> getStations() {
        List<Station> stations = this.context.select(Tables.STATION.asterisk())
                                             .from(Tables.STATION)
                                             .fetchInto(Station.class);

        return Set.copyOf(stations);
    }

    public Set<Train> getArrivals(int stationId) {
        String stationIdString = Integer.toString(stationId);

        TrainResponse response = this.client.getTrains(stationIdString);

        if (response == null) {
            throw new IllegalStateException("The train response is null for station ID %s".formatted(stationId));
        }

        TrainBody body = response.body();

        if (body == null) {
            throw new ResourceNotFoundException("The train body is null for station ID %s".formatted(stationId));
        }

        Set<Train> trains = body.trains();

        if (trains == null) {
            throw new ResourceNotFoundException("The Set of trains is null for station ID %s".formatted(stationId));
        }

        return trains.stream()
                     .filter(train -> train.line() != Line.N_A)
                     .collect(Collectors.toSet());
    }
}

package app.cta4j.controller;

import app.cta4j.model.Station;
import app.cta4j.model.Train;
import app.cta4j.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.Set;

@Controller
public final class TrainController {
    private final TrainService service;

    @Autowired
    public TrainController(TrainService service) {
        Objects.requireNonNull(service);

        this.service = service;
    }

    @QueryMapping
    public Set<Station> getStations() {
        return this.service.getStations();
    }

    @QueryMapping
    public Set<Train> getTrains(@Argument int stationId) {
        return this.service.getTrains(stationId);
    }

    @QueryMapping
    public Set<Train> followTrain(@Argument int run) {
        return this.service.followTrain(run);
    }
}

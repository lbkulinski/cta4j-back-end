package app.cta4j.controller;

import app.cta4j.model.Train;
import app.cta4j.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/trains")
public final class TrainController {
    private final TrainService service;

    @Autowired
    public TrainController(TrainService service) {
        this.service = Objects.requireNonNull(service);
    }

    @GetMapping("/{run}/stations")
    public ResponseEntity<Set<Train>> getUpcomingStations(@PathVariable int run) {
        Set<Train> stations = this.service.getUpcomingStations(run);

        stations = Set.copyOf(stations);

        return ResponseEntity.ok(stations);
    }
}

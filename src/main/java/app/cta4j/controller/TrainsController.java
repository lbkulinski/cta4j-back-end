package app.cta4j.controller;

import app.cta4j.model.Train;
import app.cta4j.service.TrainsService;
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
public final class TrainsController {
    private final TrainsService service;

    @Autowired
    public TrainsController(TrainsService service) {
        this.service = Objects.requireNonNull(service);
    }

    @GetMapping("/stations/{stationId}")
    public ResponseEntity<Set<Train>> getTrains(@PathVariable int stationId) {
        Set<Train> trains = this.service.getTrains(stationId);

        trains = Set.copyOf(trains);

        return ResponseEntity.ok(trains);
    }
}

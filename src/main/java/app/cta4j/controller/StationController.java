package app.cta4j.controller;

import app.cta4j.model.Station;
import app.cta4j.model.Train;
import app.cta4j.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/stations")
public final class StationController {
    private final StationService service;

    @Autowired
    public StationController(StationService service) {
        this.service = Objects.requireNonNull(service);
    }

    @GetMapping
    public ResponseEntity<Set<Station>> getStations() {
        Set<Station> stations = this.service.getStations();

        stations = Set.copyOf(stations);

        return ResponseEntity.ok(stations);
    }

    @GetMapping("/{stationId}/arrivals")
    public ResponseEntity<Set<Train>> getArrivals(@PathVariable int stationId) {
        Set<Train> stations = this.service.getArrivals(stationId);

        stations = Set.copyOf(stations);

        return ResponseEntity.ok(stations);
    }
}

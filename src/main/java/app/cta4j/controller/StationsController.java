package app.cta4j.controller;

import app.cta4j.model.Station;
import app.cta4j.service.StationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/stations")
public final class StationsController {
    private final StationsService service;

    @Autowired
    public StationsController(StationsService service) {
        this.service = Objects.requireNonNull(service);
    }

    @GetMapping
    public ResponseEntity<Set<Station>> getStations() {
        Set<Station> stations = this.service.getStations();

        stations = Set.copyOf(stations);

        return ResponseEntity.ok(stations);
    }
}

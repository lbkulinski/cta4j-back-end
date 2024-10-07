package app.cta4j.controller;

import app.cta4j.model.Bus;
import app.cta4j.service.BusesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/buses")
public final class BusesController {
    private final BusesService service;

    public BusesController(BusesService service) {
        this.service = Objects.requireNonNull(service);
    }

    @GetMapping("/{id}/stops")
    public ResponseEntity<Set<Bus>> getUpcomingStops(@PathVariable int id) {
        Set<Bus> stops = this.service.getUpcomingStops(id);

        stops = Set.copyOf(stops);

        return ResponseEntity.ok(stops);
    }
}

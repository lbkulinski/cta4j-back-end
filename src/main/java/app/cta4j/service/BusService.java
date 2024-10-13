package app.cta4j.service;

import app.cta4j.client.BusClient;
import app.cta4j.exception.ResourceNotFoundException;
import app.cta4j.model.Bus;
import app.cta4j.model.BusBody;
import app.cta4j.model.BusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public final class BusService {
    private final BusClient client;

    @Autowired
    public BusService(BusClient client) {
        this.client = Objects.requireNonNull(client);
    }

    public Set<Bus> getUpcomingStops(int id) {
        String idString = Integer.toString(id);

        BusResponse response = this.client.getBus(idString);

        if (response == null) {
            throw new IllegalStateException("The bus response is null for ID %d".formatted(id));
        }

        BusBody body = response.body();

        if (body == null) {
            throw new ResourceNotFoundException("The bus body is null for ID %d".formatted(id));
        }

        Set<Bus> buses = body.buses();

        if (buses == null) {
            throw new ResourceNotFoundException("The Set of buses is null for ID %d".formatted(id));
        }

        return Set.copyOf(buses);
    }
}

package app.cta4j.fetcher;

import app.cta4j.client.BusClient;
import app.cta4j.model.Bus;
import app.cta4j.model.BusBody;
import app.cta4j.model.BusResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsSubscription;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.rollbar.notifier.Rollbar;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@DgsComponent
public final class BusesFetcher {
    private final BusClient client;

    private final Rollbar rollbar;

    private final long pollingInterval;

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(BusesFetcher.class);
    }

    @Autowired
    public BusesFetcher(BusClient client, Rollbar rollbar, @Value("${cta.polling-interval}") long pollingInterval) {
        this.client = Objects.requireNonNull(client);

        this.rollbar = Objects.requireNonNull(rollbar);

        this.pollingInterval = pollingInterval;
    }

    private List<Bus> extractBuses(BusResponse response) {
        if (response == null) {
            throw new RuntimeException();
        }

        BusBody body = response.body();

        if (body == null) {
            throw new DgsEntityNotFoundException();
        }

        List<Bus> buses = body.buses();

        if (buses == null) {
            throw new DgsEntityNotFoundException();
        }

        return List.copyOf(buses);
    }

    @DgsQuery
    public List<Bus> buses(@InputArgument String routeId, @InputArgument String stopId) {
        Objects.requireNonNull(routeId);

        Objects.requireNonNull(stopId);

        BusResponse response;

        try {
            response = this.client.getBuses(routeId, stopId);
        } catch (Exception e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            BusesFetcher.LOGGER.error(message, e);

            throw new RuntimeException(e);
        }

        return this.extractBuses(response);
    }

    @DgsQuery
    public List<Bus> bus(@InputArgument String id) {
        Objects.requireNonNull(id);

        BusResponse response;

        try {
            response = this.client.getBus(id);
        } catch (Exception e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            BusesFetcher.LOGGER.error(message, e);

            throw new RuntimeException(e);
        }

        return this.extractBuses(response);
    }

    @SuppressWarnings("unchecked")
    private Mono<List<Bus>> getBuses(String routeId, String stopId) {
        Objects.requireNonNull(routeId);

        Objects.requireNonNull(stopId);

        BusResponse response;

        try {
            response = this.client.getBuses(routeId, stopId);
        } catch (Exception e) {
            this.rollbar.error(e);

            String message = e.getMessage();

            BusesFetcher.LOGGER.error(message, e);

            return Mono.just(Collections.EMPTY_LIST);
        }

        if (response == null) {
            return Mono.just(Collections.EMPTY_LIST);
        }

        BusBody body = response.body();

        if (body == null) {
            return Mono.just(Collections.EMPTY_LIST);
        }

        List<Bus> buses = body.buses();

        if (buses == null) {
            buses = Collections.EMPTY_LIST;
        }

        List<Bus> copy = List.copyOf(buses);

        return Mono.just(copy);
    }

    @DgsSubscription
    public Publisher<List<Bus>> busesSubscribe(@InputArgument String routeId, @InputArgument String stopId) {
        Objects.requireNonNull(routeId);

        Objects.requireNonNull(stopId);

        Duration duration = Duration.ofSeconds(this.pollingInterval);

        return Flux.interval(Duration.ZERO, duration)
                   .flatMap(tick -> this.getBuses(routeId, stopId));
    }
}

package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record BusResponse(@JsonAlias("bustime-response") BusBody body) {
}

package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Set;

public record BusBody(@JsonAlias("prd") Set<Bus> buses) {
}

package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record FollowBody(@JsonAlias("eta") List<Train> trains) {
}

package app.cta4j.client;

import app.cta4j.model.FollowResponse;
import app.cta4j.model.TrainResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface TrainClient {
    @GetExchange("/ttarrivals.aspx")
    TrainResponse getTrains(@RequestParam("mapid") String stationId);

    @GetExchange("/ttfollow.aspx")
    FollowResponse getTrain(@RequestParam("runnumber") int run);
}

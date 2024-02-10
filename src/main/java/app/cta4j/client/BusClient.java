package app.cta4j.client;

import app.cta4j.model.BusResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface BusClient {
    @GetExchange("/getpredictions")
    BusResponse getBuses(@RequestParam("rt") String routeId, @RequestParam("stpid") int stopId);

    @GetExchange("/getpredictions")
    BusResponse followBus(@RequestParam("vid") int id);
}

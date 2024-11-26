package app.cta4j.config;

import app.cta4j.client.BusClient;
import app.cta4j.client.TrainClient;
import app.cta4j.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Objects;

@Configuration
public class HttpClientConfiguration {
    private final SecretService secretService;

    @Autowired
    public HttpClientConfiguration(SecretService secretService) {
        this.secretService = Objects.requireNonNull(secretService);
    }

    @Bean
    public TrainClient trainClient() {
        String key = "TRAIN_API_KEY";

        String apiKey = this.secretService.getSecret(key);

        if (apiKey == null) {
            throw new IllegalStateException("TRAIN_API_KEY is required");
        }

        String baseUrl = """
        https://lapi.transitchicago.com/api/1.0?key=%s&outputType=json""".formatted(apiKey);

        RestClient restClient = RestClient.create(baseUrl);

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter)
                                                                                 .build();

        return httpServiceProxyFactory.createClient(TrainClient.class);
    }

    @Bean
    public BusClient busClient() {
        String key = "BUS_API_KEY";

        String apiKey = this.secretService.getSecret(key);

        if (apiKey == null) {
            throw new IllegalStateException("BUS_API_KEY is required");
        }

        String baseUrl = "https://ctabustracker.com/bustime/api/v2?key=%s&format=json".formatted(apiKey);

        RestClient restClient = RestClient.create(baseUrl);

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter)
                                                                                 .build();

        return httpServiceProxyFactory.createClient(BusClient.class);
    }
}

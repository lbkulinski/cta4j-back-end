package app.cta4j.config;

import app.cta4j.client.TrainClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Objects;

@Configuration
public class HttpClientConfiguration {
    @Bean
    public TrainClient trainClient(@Value("${cta.train-api-key}") String apiKey) {
        Objects.requireNonNull(apiKey);

        String baseUrl = """
        https://lapi.transitchicago.com/api/1.0?key=%s&outputType=json""".formatted(apiKey);

        RestClient restClient = RestClient.create(baseUrl);

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter)
                                                                                 .build();

        return httpServiceProxyFactory.createClient(TrainClient.class);
    }
}

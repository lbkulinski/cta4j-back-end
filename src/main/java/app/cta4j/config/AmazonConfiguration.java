package app.cta4j.config;

import com.amazonaws.secretsmanager.caching.SecretCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Configuration
public class AmazonConfiguration {
    @Bean
    public SecretCache secretCache() {
        SecretsManagerClient client = SecretsManagerClient.builder()
                                                          .region(Region.US_EAST_2)
                                                          .build();

        return new SecretCache(client);
    }
}

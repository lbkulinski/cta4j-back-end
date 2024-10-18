package app.cta4j.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {
    @Bean
    public CacheManager cacheManager() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                                                    .expireAfterWrite(1L, TimeUnit.DAYS);

        String[] cacheNames = {"stations", "routes", "directions", "stops"};

        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheNames);

        cacheManager.setCaffeine(caffeine);

        return cacheManager;
    }
}

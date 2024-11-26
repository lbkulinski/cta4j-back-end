package app.cta4j.service;

import com.amazonaws.secretsmanager.caching.SecretCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public final class SecretService {
    private final SecretCache cache;

    private final String secretName;

    private final ObjectMapper mapper;

    @Autowired
    public SecretService(SecretCache cache, @Value("${aws.secret-name}") String secretName, ObjectMapper mapper) {
        this.cache = Objects.requireNonNull(cache);

        this.secretName = Objects.requireNonNull(secretName);

        this.mapper = Objects.requireNonNull(mapper);
    }

    public String getSecret(String key) {
        String secret = this.cache.getSecretString(this.secretName);

        JsonNode node;

        try {
            node = this.mapper.readTree(secret);
        } catch (JsonProcessingException e) {
            return null;
        }

        JsonNode value = node.get(key);

        if (value == null) {
            return null;
        }

        return value.asText();
    }
}

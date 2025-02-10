package com.michael.container.registry.cache;

import com.michael.container.registry.model.DurationValue;
import com.michael.container.registry.model.RegisterServiceResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class RegistryCache {
  private static final Duration REGISTRATION_EXPIRATION_DURATION = Duration.ofMinutes(2L);

  private final Map<String, Map<RegisterServiceResponse, DurationValue>> registerServiceResponseSet;

  public RegistryCache() {
    registerServiceResponseSet = new ConcurrentHashMap<>();
  }

  public Map<String, Map<RegisterServiceResponse, DurationValue>> getRegisterServiceResponseSet() {
    return registerServiceResponseSet;
  }

  public static Instant generateNewExpiration() {
    return Instant.now().plus(REGISTRATION_EXPIRATION_DURATION);
  }
}

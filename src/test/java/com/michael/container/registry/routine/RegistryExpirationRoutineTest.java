package com.michael.container.registry.routine;

import com.michael.container.registry.cache.RegistryCache;
import com.michael.container.registry.cache.crud.CrudRegistry;
import com.michael.container.registry.model.DurationValue;
import com.michael.container.registry.model.RegisterServiceResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegistryExpirationRoutineTest {
  RegistryExpirationRoutine routine;
  CrudRegistry crudRegistry;
  RegistryCache registryCache;

  @BeforeEach
  void setup() {
    registryCache = new RegistryCache();
    crudRegistry = new CrudRegistry(registryCache);
    routine = new RegistryExpirationRoutine(crudRegistry);
  }

  @Test
  void expirationRoutineCheck_RemovesAllExpiredRecords() {
    RegisterServiceResponse response1 =
        new RegisterServiceResponse(
            "applicationName", 1, "ip", 8080, new HashSet<>(), new HashMap<>());
    RegisterServiceResponse response2 =
        new RegisterServiceResponse(
            "otherApplicationName", 1, "otherhost", 9090, new HashSet<>(), new HashMap<>());

    Map<String, Map<RegisterServiceResponse, DurationValue>> map = new ConcurrentHashMap<>();
    map.put(
        "applicationName",
        new ConcurrentHashMap<>(
            Map.of(response1, new DurationValue(Instant.now().minusSeconds(5)))));
    map.put(
        "otherApplicationName",
        new ConcurrentHashMap<>(Map.of(response2, new DurationValue(Instant.MAX))));

    registryCache.getRegisterServiceResponseSet().putAll(map);

    routine.expirationRoutineCheck();

    Set<RegisterServiceResponse> updatedResponse =
        registryCache.getRegisterServiceResponseSet().values().stream()
            .flatMap(x -> x.keySet().stream())
            .collect(Collectors.toSet());

    Assertions.assertAll(
        () -> Assertions.assertEquals(1, updatedResponse.size()),
        () -> Assertions.assertTrue(updatedResponse.contains(response2)));
  }
}

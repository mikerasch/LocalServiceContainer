package com.michael.container.registry.cache.crud;

import com.michael.container.registry.cache.RegistryCache;
import com.michael.container.registry.model.DurationValue;
import com.michael.container.registry.model.RegisterServiceResponse;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class CrudRegistry {
  private final RegistryCache registryCache;

  public CrudRegistry(RegistryCache registryCache) {
    this.registryCache = registryCache;
  }

  public void insert(RegisterServiceResponse registerServiceResponse) {
    String applicationName = registerServiceResponse.applicationName();
    getCache()
        .computeIfPresent(
            applicationName,
            (key, existingServices) -> {
              existingServices.put(
                  registerServiceResponse,
                  new DurationValue(RegistryCache.generateNewExpiration()));
              return existingServices;
            });
    getCache()
        .putIfAbsent(
            applicationName,
            new HashMap<>(
                Map.of(
                    registerServiceResponse,
                    new DurationValue(RegistryCache.generateNewExpiration()))));
  }

  public Map<String, Map<RegisterServiceResponse, DurationValue>> fetchAll() {
    return new HashMap<>(registryCache.getRegisterServiceResponseSet());
  }

  public Optional<RegisterServiceResponse> findOne(
      String applicationName, String url, int port, int version) {
    return getCache().getOrDefault(applicationName, new HashMap<>()).keySet().stream()
        .filter(
            serviceResponse ->
                serviceResponse.ip().equals(url)
                    && serviceResponse.port() == port
                    && serviceResponse.applicationVersion() == version)
        .findFirst();
  }

  public void remove(String applicationName, String ip, int applicationVersion, int port) {
    getCache()
        .getOrDefault(applicationName, new HashMap<>())
        .entrySet()
        .removeIf(
            entry ->
                entry.getKey().ip().equals(ip)
                    && entry.getKey().port() == port
                    && entry.getKey().applicationVersion() == applicationVersion);
  }

  private Map<String, Map<RegisterServiceResponse, DurationValue>> getCache() {
    return registryCache.getRegisterServiceResponseSet();
  }
}

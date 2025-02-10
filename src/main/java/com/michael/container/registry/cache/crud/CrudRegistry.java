package com.michael.container.registry.cache.crud;

import com.michael.container.registry.cache.RegistryCache;
import com.michael.container.registry.model.DeregisterEvent;
import com.michael.container.registry.model.DurationValue;
import com.michael.container.registry.model.RegisterEvent;
import com.michael.container.registry.model.RegisterServiceResponse;
import java.util.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CrudRegistry {
  private final RegistryCache registryCache;
  private final ApplicationEventPublisher eventPublisher;

  public CrudRegistry(RegistryCache registryCache, ApplicationEventPublisher eventPublisher) {
    this.registryCache = registryCache;
    this.eventPublisher = eventPublisher;
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
    eventPublisher.publishEvent(
        new RegisterEvent(
            registerServiceResponse.applicationName(),
            registerServiceResponse.ip(),
            registerServiceResponse.applicationVersion(),
            registerServiceResponse.port()));
  }

  public Map<String, Map<RegisterServiceResponse, DurationValue>> fetchAll() {
    return new HashMap<>(registryCache.getApplicationToRegisterServiceMap());
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
    eventPublisher.publishEvent(new DeregisterEvent(applicationName, ip, applicationVersion, port));
  }

  private Map<String, Map<RegisterServiceResponse, DurationValue>> getCache() {
    return registryCache.getApplicationToRegisterServiceMap();
  }
}

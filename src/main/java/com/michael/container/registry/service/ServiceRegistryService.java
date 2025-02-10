package com.michael.container.registry.service;

import com.michael.container.registry.cache.crud.CrudRegistry;
import com.michael.container.registry.model.RegisterServiceRequest;
import com.michael.container.registry.model.RegisterServiceResponse;
import com.michael.container.registry.model.RemoveServiceRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class ServiceRegistryService {
  private final ConversionService conversionService;
  private final CrudRegistry crudRegistry;
  private final ApplicationEventPublisher eventPublisher;

  public ServiceRegistryService(
      ConversionService conversionService,
      CrudRegistry crudRegistry,
      ApplicationEventPublisher eventPublisher) {
    this.conversionService = conversionService;
    this.crudRegistry = crudRegistry;
    this.eventPublisher = eventPublisher;
  }

  public void registerService(RegisterServiceRequest registerServiceRequest) {
    crudRegistry.insert(
        Objects.requireNonNull(
            conversionService.convert(registerServiceRequest, RegisterServiceResponse.class)));
  }

  public Map<String, Set<RegisterServiceResponse>> fetchAll() {
    Map<String, Set<RegisterServiceResponse>> map = new ConcurrentHashMap<>();

    crudRegistry.fetchAll().forEach((key, value) -> map.put(key, new HashSet<>(value.keySet())));

    return map;
  }

  public void removeService(RemoveServiceRequest removeServiceRequest) {
    crudRegistry.remove(
        removeServiceRequest.applicationName(),
        removeServiceRequest.ip(),
        removeServiceRequest.version(),
        removeServiceRequest.port());
  }
}

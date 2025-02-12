package com.michael.container.registry.controller;

import com.michael.container.registry.model.RegisterServiceRequest;
import com.michael.container.registry.model.RegisterServiceResponse;
import com.michael.container.registry.model.RemoveServiceRequest;
import com.michael.container.registry.service.ServiceRegistryService;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service-registry")
@Validated
public class ServiceRegistryController {
  private final ServiceRegistryService registryService;

  public ServiceRegistryController(ServiceRegistryService registryService) {
    this.registryService = registryService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void registerService(@RequestBody @Valid RegisterServiceRequest registerServiceRequest) {
    registryService.registerService(registerServiceRequest);
  }

  @GetMapping
  public Map<String, Set<RegisterServiceResponse>> retrieveAllServices() {
    return registryService.fetchAll();
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deregisterService(@RequestBody @Valid RemoveServiceRequest deregisterRequest) {
    registryService.removeService(deregisterRequest);
  }
}

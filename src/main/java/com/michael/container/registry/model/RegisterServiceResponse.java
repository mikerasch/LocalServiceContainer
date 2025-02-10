package com.michael.container.registry.model;

import java.util.Map;
import java.util.Set;

public record RegisterServiceResponse(
    String applicationName,
    int applicationVersion,
    String ip,
    int port,
    Set<String> dependsOn,
    Map<String, String> metaData) {}

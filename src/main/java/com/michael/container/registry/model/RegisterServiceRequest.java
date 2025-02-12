package com.michael.container.registry.model;

import com.michael.container.validation.annotation.HttpUrl;
import java.util.Map;
import java.util.Set;

public record RegisterServiceRequest(
    String applicationName,
    int applicationVersion,
    @HttpUrl String url,
    int port,
    Set<String> dependsOn,
    Map<String, String> metaData) {}

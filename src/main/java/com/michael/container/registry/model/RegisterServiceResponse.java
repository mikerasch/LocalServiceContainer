package com.michael.container.registry.model;

import com.michael.container.registry.enums.Tag;
import java.util.Map;
import java.util.Set;

public record RegisterServiceResponse(
    String applicationName,
    int applicationVersion,
    String url,
    int port,
    Set<String> dependsOn,
    Map<Tag, String> metaData) {}

package com.michael.container.registry.model;

import com.michael.container.registry.enums.Tag;
import com.michael.container.validation.annotation.HttpUrl;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public record RegisterServiceRequest(
    @NotNull String applicationName,
    @NotNull int applicationVersion,
    @HttpUrl @NotNull String url,
    @NotNull int port,
    @Nullable Set<String> dependsOn,
    @Nullable Map<Tag, String> metaData) {}

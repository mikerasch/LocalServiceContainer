package com.michael.container.registry.model;

import com.michael.container.validation.annotation.HttpUrl;
import jakarta.validation.constraints.NotNull;

public record RemoveServiceRequest(
    @NotNull String applicationName,
    @HttpUrl @NotNull String url,
    @NotNull int version,
    @NotNull int port) {}

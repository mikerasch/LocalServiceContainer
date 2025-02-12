package com.michael.container.heartbeat.model;

import com.michael.container.validation.annotation.HttpUrl;
import jakarta.validation.constraints.NotNull;

public record HeartbeatRequest(
    @NotNull String applicationName,
    @HttpUrl @NotNull String url,
    @NotNull int port,
    @NotNull int applicationVersion) {}

package com.michael.container.heartbeat.model;

import com.michael.container.validation.annotation.HttpUrl;

public record HeartbeatRequest(
    String applicationName, @HttpUrl String url, int port, int applicationVersion) {}

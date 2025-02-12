package com.michael.container.heartbeat.model;

public record HeartbeatRequest(
    String applicationName, String url, int port, int applicationVersion) {}

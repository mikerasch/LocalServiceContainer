package com.michael.container.heartbeat.model;

public record HeartbeatRequest(
    String applicationName, String ip, int port, int applicationVersion) {}

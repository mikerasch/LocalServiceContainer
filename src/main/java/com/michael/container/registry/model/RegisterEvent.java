package com.michael.container.registry.model;

public record RegisterEvent(String applicationName, String ip, int version, int port) {}

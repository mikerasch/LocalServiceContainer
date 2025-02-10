package com.michael.container.registry.model;

public record DeregisterEvent(String applicationName, String ip, int version, int port) {}

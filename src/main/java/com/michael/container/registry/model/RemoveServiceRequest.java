package com.michael.container.registry.model;

public record RemoveServiceRequest(String applicationName, String ip, int version, int port) {}

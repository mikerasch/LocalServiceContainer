package com.michael.container.registry.model;

public record RemoveServiceRequest(String applicationName, String url, int version, int port) {}

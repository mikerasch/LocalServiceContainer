package com.michael.container.registry.model;

import com.michael.container.validation.annotation.HttpUrl;

public record RemoveServiceRequest(
    String applicationName, @HttpUrl String url, int version, int port) {}

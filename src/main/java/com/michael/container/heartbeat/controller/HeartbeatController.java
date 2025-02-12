package com.michael.container.heartbeat.controller;

import com.michael.container.heartbeat.model.HeartbeatRequest;
import com.michael.container.heartbeat.model.HeartbeatResponse;
import com.michael.container.heartbeat.service.HeartbeatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartbeatController {
  private final HeartbeatService heartbeatService;

  public HeartbeatController(HeartbeatService heartbeatService) {
    this.heartbeatService = heartbeatService;
  }

  @PostMapping
  public HeartbeatResponse heartbeat(@RequestBody @Valid HeartbeatRequest heartbeatRequest) {
    return heartbeatService.heartbeat(heartbeatRequest);
  }
}

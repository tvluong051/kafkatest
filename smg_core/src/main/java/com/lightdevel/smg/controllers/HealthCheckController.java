package com.lightdevel.smg.controllers;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api("Health check")
@RestController
@RequestMapping("/api")
public class HealthCheckController {

  @GetMapping("/v0/ping")
  public String healthCheck() {
    return "healthy";
  }
}

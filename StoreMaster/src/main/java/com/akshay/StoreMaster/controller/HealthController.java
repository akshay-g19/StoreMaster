package com.akshay.StoreMaster.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class HealthController implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().withDetail("status", "Application is healthy").build();
    }

}

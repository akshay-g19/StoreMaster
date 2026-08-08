package com.akshay.StoreMaster.controller;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;

public class HealthController implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().withDetail("status", "Application is healthy").build();
    }

}

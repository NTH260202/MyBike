package com.swd.bike.handler.health;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.health.HealthCheckRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class CheckHealthHandler extends RequestHandler<HealthCheckRequest, StatusResponse> {
    @Override
    public StatusResponse handle(HealthCheckRequest request) {
        log.info("Current SERVER time: {}", LocalDateTime.now());
        return new StatusResponse(true);
    }
}

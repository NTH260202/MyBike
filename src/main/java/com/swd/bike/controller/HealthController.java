package com.swd.bike.controller;

import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.health.HealthCheckRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController extends BaseController {

    @GetMapping("/api/public/v1/health")
    public ResponseEntity<ResponseBase<StatusResponse>> checkHealth() {
        return this.execute(new HealthCheckRequest());
    }

}

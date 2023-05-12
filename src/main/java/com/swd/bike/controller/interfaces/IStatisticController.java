package com.swd.bike.controller.interfaces;

import com.amazonaws.services.servermigration.model.GenerateTemplateRequest;
import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.responseCode.response.ResponseCodeResponse;
import com.swd.bike.dto.statistic.request.GetStatisticRequest;
import com.swd.bike.dto.statistic.response.GetStatisticResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Tag(name = "[CMS] Statistic Controller", description = "Thao tác với số liệu")
@RequestMapping("/api/v1/statistics")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface IStatisticController {
    @GetMapping()
    ResponseEntity<ResponseBase<GetStatisticResponse>> getStatistic(@Valid @ParameterObject GetStatisticRequest request);
}

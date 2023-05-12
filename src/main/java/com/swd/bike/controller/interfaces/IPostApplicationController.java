package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.application.request.GetAllApplicationsRequest;
import com.swd.bike.dto.application.response.AppliedPostResponse;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.userPost.response.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api/v1/applications")
@Tag(name = "[User] Applications controller")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface IPostApplicationController {
    @GetMapping
    @Operation(summary = "Get all applied posts")
    ResponseEntity<ResponseBase<PageResponse<AppliedPostResponse>>> getAllApplyingPost(@ParameterObject @Valid GetAllApplicationsRequest request);

}

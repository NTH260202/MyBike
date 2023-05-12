package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.trip.request.GetPageByFilterRequest;
import com.swd.bike.dto.trip.request.GetTripDetailRequest;
import com.swd.bike.dto.trip.response.GetTripDetailResponse;
import com.swd.bike.dto.trip.response.GetTripPageResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/cms/v1/trips")
@Tag(name = "[Cms] Trip Controller", description = "Thao tác với chuyến đi")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface ICmsTripController {
    @GetMapping("/{id}")
    ResponseEntity<ResponseBase<GetTripDetailResponse>> getDetailById(
            @PathVariable long id);

    @GetMapping()
    ResponseEntity<ResponseBase<PageResponse<GetTripPageResponse>>> getPageByFilter(
            @ParameterObject GetPageByFilterRequest request);
}

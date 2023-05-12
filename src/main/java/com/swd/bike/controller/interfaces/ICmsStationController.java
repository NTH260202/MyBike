package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.station.reponse.*;
import com.swd.bike.dto.station.request.*;
import com.swd.bike.enums.StationStatus;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/cms/v1/stations")
@Tag(name = "[Cms] Station Controller", description = "Thao tác với trạm dừng")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface ICmsStationController {
    @PostMapping
    ResponseEntity<ResponseBase<CreateStationResponse>> create(
            @RequestBody @Valid CreateStationRequest request);

    @PutMapping("/{id}")
    ResponseEntity<ResponseBase<UpdateStationResponse>> update(
            @PathVariable long id,
            @RequestBody @Valid UpdateStationRequest request);

    @PutMapping("/{id}/status")
    ResponseEntity<ResponseBase<UpdateStationStatusResponse>> update(
            @PathVariable long id,
            @RequestParam(required = true) StationStatus status);

    @GetMapping("/{id}")
    ResponseEntity<ResponseBase<GetDetailResponse>> getDetailById(
            @PathVariable long id);

    @GetMapping()
    ResponseEntity<ResponseBase<PageResponse<StationPageResponse>>> getPageByFilter(
            @ParameterObject GetPageByFilterRequest request);
}

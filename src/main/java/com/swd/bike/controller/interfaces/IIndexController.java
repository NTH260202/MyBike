package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.index.GetStationsRequest;
import com.swd.bike.dto.userPost.request.GetActivePostsRequest;
import com.swd.bike.dto.userPost.response.PostResponse;
import com.swd.bike.dto.userPost.response.StationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "[User] Home Controller", description = "User for all general information")
@RequestMapping("/api/index/v1")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface IIndexController {

    @GetMapping("/posts")
    @Operation(summary = "Get all active posts in home page")
    ResponseEntity<ResponseBase<PageResponse<PostResponse>>> getAllIndexPosts(@ParameterObject GetActivePostsRequest request);

    @GetMapping("/stations")
    @Operation(summary = "Get all station. Pass 'fromStationId' to get all stations from one station")
    ResponseEntity<ResponseBase<ListResponse<StationResponse>>> getAllStations(@ParameterObject GetStationsRequest request);
}

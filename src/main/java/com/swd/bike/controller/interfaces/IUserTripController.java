package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userPost.response.StationResponse;
import com.swd.bike.dto.userTrip.request.FeedbackTripRequest;
import com.swd.bike.dto.userTrip.request.GetAllTripsRequest;
import com.swd.bike.dto.userTrip.response.TripDetailResponse;
import com.swd.bike.dto.userTrip.response.TripResponse;
import com.swd.bike.enums.TripAction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

@RequestMapping("/api/v1/trips")
@Tag(name = "[User] Trip Controller", description = "Thao tác với trip")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface IUserTripController {
    @GetMapping("")
    @Operation(summary = "Get all my trips")
    ResponseEntity<ResponseBase<PageResponse<TripResponse>>> getAllTrips(@ParameterObject GetAllTripsRequest request);

    @GetMapping("/ongoing")
    @Operation(summary = "Get on-going trip")
    ResponseEntity<ResponseBase<TripDetailResponse>> getCurrentTrip();

    @GetMapping("/{id}")
    @Operation(summary = "Get trip information")
    ResponseEntity<ResponseBase<TripDetailResponse>> getTrip(@PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Update trip status")
    ResponseEntity<ResponseBase<StatusResponse>> updateTrip(@PathVariable Long id, @RequestParam TripAction action);

    @PostMapping("/{id}/feedbacks")
    @Operation(summary = "(Picker) Feedback a trip")
    ResponseEntity<ResponseBase<StatusResponse>> feedbackTrip(@PathVariable Long id, @Valid @RequestBody FeedbackTripRequest request);

}

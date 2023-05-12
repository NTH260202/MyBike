package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.user.request.UpdateUserRequest;
import com.swd.bike.dto.user.response.UserDetailResponse;
import com.swd.bike.dto.user.response.UserResponse;
import com.swd.bike.dto.vehicle.request.RegisterVehicleRequest;
import com.swd.bike.dto.vehicle.request.UpdateVehicleRequest;
import com.swd.bike.dto.vehicle.response.VehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "User Controller", description = "Thao tác với users")
@RequestMapping(value = "/api/v1/users")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface IUserController {

    @Operation(summary = "Get current user info")
    @GetMapping("/me")
    ResponseEntity<ResponseBase<UserDetailResponse>> getUserInfo();

    @Operation(summary = "Update User")
    @PutMapping("/me")
    ResponseEntity<ResponseBase<StatusResponse>> updateUserInfo(@Valid @RequestBody UpdateUserRequest request);

    @Operation(summary = "Register vehicle")
    @PostMapping("/me/vehicle")
    ResponseEntity<ResponseBase<StatusResponse>> registerVehicle(@Valid @RequestBody RegisterVehicleRequest request);

    @Operation(summary = "Get current user's vehicle")
    @GetMapping("/me/vehicle")
    ResponseEntity<ResponseBase<VehicleResponse>> getVehicle();

    @Operation(summary = "Update current user's vehicle")
    @PutMapping("/me/vehicle")
    ResponseEntity<ResponseBase<StatusResponse>> updateVehicle(@RequestBody UpdateVehicleRequest request);

    @Operation(summary = "Delete current user's vehicle")
    @DeleteMapping("/me/vehicle")
    ResponseEntity<ResponseBase<StatusResponse>> deleteVehicle();

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    ResponseEntity<ResponseBase<UserResponse>> getUser(@PathVariable String id);
}

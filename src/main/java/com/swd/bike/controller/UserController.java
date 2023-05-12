package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IUserController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.user.request.GetUserDetailRequest;
import com.swd.bike.dto.user.request.GetUserInfoRequest;
import com.swd.bike.dto.user.request.UpdateUserRequest;
import com.swd.bike.dto.user.response.UserDetailResponse;
import com.swd.bike.dto.user.response.UserResponse;
import com.swd.bike.dto.vehicle.request.DeleteVehicleRequest;
import com.swd.bike.dto.vehicle.request.GetUserVehicleRequest;
import com.swd.bike.dto.vehicle.request.RegisterVehicleRequest;
import com.swd.bike.dto.vehicle.request.UpdateVehicleRequest;
import com.swd.bike.dto.vehicle.response.VehicleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController implements IUserController {

    @Override
    public ResponseEntity<ResponseBase<UserDetailResponse>> getUserInfo() {
        GetUserInfoRequest request = new GetUserInfoRequest();
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> updateUserInfo(UpdateUserRequest request) {
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> registerVehicle(RegisterVehicleRequest request) {
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<VehicleResponse>> getVehicle() {
        GetUserVehicleRequest request = new GetUserVehicleRequest();
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> updateVehicle(UpdateVehicleRequest request) {
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> deleteVehicle() {
        DeleteVehicleRequest request = new DeleteVehicleRequest();
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<UserResponse>> getUser(String id) {
        return this.execute(new GetUserDetailRequest(id));
    }

}

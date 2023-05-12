package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IExponentPushTokenController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.token.request.AddExpoTokenRequest;
import com.swd.bike.dto.token.request.DeleteExpoTokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExponentPushTokenController extends BaseController implements IExponentPushTokenController {

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> addToken(AddExpoTokenRequest request) {
        request.setUserId(this.getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> deleteToken(DeleteExpoTokenRequest request) {
        request.setUserId(this.getUserId());
        return this.execute(request);
    }
}

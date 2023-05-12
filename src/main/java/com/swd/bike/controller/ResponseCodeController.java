package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IResponseCodeController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.responseCode.request.GetAllResponseCodesRequest;
import com.swd.bike.dto.responseCode.request.GetResponseCodeRequest;
import com.swd.bike.dto.responseCode.response.ResponseCodeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponseCodeController extends BaseController implements IResponseCodeController {

    @Override
    public ResponseEntity<ResponseBase<ListResponse<ResponseCodeResponse>>> getAllResponseCodes() {
        return this.execute(new GetAllResponseCodesRequest());
    }

    @Override
    public ResponseEntity<ResponseBase<ResponseCodeResponse>> getResponseCode(int code) {
        return this.execute(new GetResponseCodeRequest(code));
    }
}

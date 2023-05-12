package com.swd.bike.handler.responseCode;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.responseCode.request.GetResponseCodeRequest;
import com.swd.bike.dto.responseCode.response.ResponseCodeResponse;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetResponseCodeHandler extends RequestHandler<GetResponseCodeRequest, ResponseCodeResponse> {

    @Override
    public ResponseCodeResponse handle(GetResponseCodeRequest request) {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if(responseCode.getCode() == request.getCode()) {
                return new ResponseCodeResponse()
                        .setCode(responseCode.getCode())
                        .setMessage(responseCode.getMessage());
            }
        }
        throw new InternalException(ResponseCode.RESPONSE_CODE_INVALID);
    }
}

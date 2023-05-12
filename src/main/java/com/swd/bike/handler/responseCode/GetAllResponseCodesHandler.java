package com.swd.bike.handler.responseCode;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.responseCode.request.GetAllResponseCodesRequest;
import com.swd.bike.dto.responseCode.response.ResponseCodeResponse;
import com.swd.bike.enums.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllResponseCodesHandler extends RequestHandler<GetAllResponseCodesRequest, ListResponse<ResponseCodeResponse>> {

    @Override
    public ListResponse<ResponseCodeResponse> handle(GetAllResponseCodesRequest request) {
        List<ResponseCodeResponse> responseCodes = new ArrayList<>();
        Arrays.asList(ResponseCode.values())
                .forEach(responseCode -> responseCodes.add(
                        new ResponseCodeResponse().setCode(responseCode.getCode())
                                .setMessage(responseCode.getMessage())
                ));
        return new ListResponse(responseCodes);
    }
}

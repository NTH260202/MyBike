package com.swd.bike.controller;

import com.amazonaws.services.servermigration.model.GenerateTemplateRequest;
import com.swd.bike.controller.interfaces.IStatisticController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.responseCode.response.ResponseCodeResponse;
import com.swd.bike.dto.statistic.request.GetStatisticRequest;
import com.swd.bike.dto.statistic.response.GetStatisticResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController extends BaseController implements IStatisticController {

    @Override
    public ResponseEntity<ResponseBase<GetStatisticResponse>> getStatistic(GetStatisticRequest request) {
        return execute(request);
    }
}

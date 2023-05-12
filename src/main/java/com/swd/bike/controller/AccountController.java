package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.ICmsAccountController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.account.request.UpdateAccountStatusRequest;
import com.swd.bike.dto.account.response.UpdateAccountStatusResponse;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.account.response.GetAccountDetailResponse;
import com.swd.bike.dto.account.response.AccountPageResponse;
import com.swd.bike.dto.account.request.GetDetailRequest;
import com.swd.bike.dto.account.request.GetPageByFilterRequest;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController extends BaseController implements ICmsAccountController {
    @Override
    public ResponseEntity<ResponseBase<GetAccountDetailResponse>> getDetailById(
            @PathVariable String id) {
        GetDetailRequest request = new GetDetailRequest();
        request.setId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<UpdateAccountStatusResponse>> updateAccountStatus(String id, UpdateAccountStatusRequest request) {
        request.setId(id);
        return this.execute(request);
    }


    @Override
    public ResponseEntity<ResponseBase<PageResponse<AccountPageResponse>>> getPageByFilter(
            @ParameterObject GetPageByFilterRequest request) {
        return this.execute(request);
    }
}

package com.swd.bike.controller.interfaces;


import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.account.request.GetPageByFilterRequest;
import com.swd.bike.dto.account.request.UpdateAccountStatusRequest;
import com.swd.bike.dto.account.response.AccountPageResponse;
import com.swd.bike.dto.account.response.GetAccountDetailResponse;
import com.swd.bike.dto.account.response.UpdateAccountStatusResponse;
import com.swd.bike.dto.common.PageResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/cms/v1/accounts")
@Tag(name = "[Cms] Account Controller", description = "Thao tác với tài khoản")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface ICmsAccountController {
    @GetMapping("/{id}")
    ResponseEntity<ResponseBase<GetAccountDetailResponse>> getDetailById(
            @PathVariable("id") String id);

    @PutMapping("/{id}")
    ResponseEntity<ResponseBase<UpdateAccountStatusResponse>> updateAccountStatus(
            @PathVariable("id") String id, @ParameterObject UpdateAccountStatusRequest request);

    @GetMapping("")
    ResponseEntity<ResponseBase<PageResponse<AccountPageResponse>>> getPageByFilter(
            @ParameterObject GetPageByFilterRequest request);
}

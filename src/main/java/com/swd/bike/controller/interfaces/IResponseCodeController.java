
package com.swd.bike.controller.interfaces;

import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.responseCode.response.ResponseCodeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "[Public] Response Code Controller", description = "Thao tác với mã lỗi")
@RequestMapping("/api/v1/response-codes")
public interface IResponseCodeController {
    @Operation(summary = "Get All Response Code")
    @GetMapping()
    ResponseEntity<ResponseBase<ListResponse<ResponseCodeResponse>>> getAllResponseCodes();

    @Operation(summary = "Get Response Code by Code")
    @GetMapping("/{code}")
    ResponseEntity<ResponseBase<ResponseCodeResponse>> getResponseCode(@PathVariable int code);
}
package com.swd.bike.core;

import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BaseController {
    @Autowired
    protected SpringBus springBus;
    @Autowired
    private IAccountService accountService;

    protected <T extends RequestData, I extends ResponseData> ResponseEntity<ResponseBase<I>> execute(T request) {
        return ResponseEntity.ok(new ResponseBase<>(this.springBus.execute(request)));
    }

    protected String getSubjectId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null ||
                securityContext.getAuthentication() == null ||
                StringUtils.isBlank(securityContext.getAuthentication().getName())) {
            throw new InternalException(ResponseCode.UNAUTHORIZED_REQUEST);
        }
        return securityContext.getAuthentication().getName();
    }

    protected String getUserId() {
        return accountService.getIdBySubjectId(getSubjectId());
    }

}

package com.swd.bike.handler.account;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.account.request.GetPageByFilterRequest;
import com.swd.bike.dto.account.response.AccountPageResponse;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetPageAccountByFilterHandler extends RequestHandler<GetPageByFilterRequest, PageResponse<AccountPageResponse>> {
    private final AccountService accountService;

    @Override
    @Cacheable(cacheNames = "accounts", key = "#request.toString()")
    public PageResponse<AccountPageResponse> handle(GetPageByFilterRequest request) {
        Page<Account> accountPage = accountService.getAccountsByFilter(request.getSpecification(), request.getPageable());
        PageResponse<AccountPageResponse> response = new PageResponse<>(accountPage);
        response.setItems(accountPage.getContent()
                .stream()
                .map(account -> convertEntityToDTO(account))
                .collect(Collectors.toList()));
        return response;
    }

    private AccountPageResponse convertEntityToDTO(Account account) {
        return AccountPageResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .phone(account.getPhone())
                .averagePoint(account.getAveragePoint())
                .card(account.getCard())
                .avatar(account.getAvatar())
                .modifiedAt(account.getModifiedAt())
                .createdAt(account.getCreatedAt())
                .status(account.getStatus())
                .build();
    }
}

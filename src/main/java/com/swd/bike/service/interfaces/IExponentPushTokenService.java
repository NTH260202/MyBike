package com.swd.bike.service.interfaces;

import com.swd.bike.entity.ExponentPushToken;

import java.util.List;

public interface IExponentPushTokenService {
    List<String> getAllTokensByAccountId(String accountId);

    List<String> getAllTokens();

    ExponentPushToken getByToken(String token);

    ExponentPushToken save(ExponentPushToken token);

    void delete(ExponentPushToken token);
}

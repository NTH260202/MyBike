package com.swd.bike.service;

import com.swd.bike.entity.ExponentPushToken;
import com.swd.bike.repository.ExponentPushTokenRepository;
import com.swd.bike.service.interfaces.IExponentPushTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExponentPushTokenService implements IExponentPushTokenService {
    private final ExponentPushTokenRepository exponentPushTokenRepository;


    @Override
    public List<String> getAllTokensByAccountId(String accountId) {
        return exponentPushTokenRepository.findAllTokensByAccountId(accountId);
    }

    @Override
    public List<String> getAllTokens() {
        return exponentPushTokenRepository.findAllTokens();
    }

    @Override
    public ExponentPushToken getByToken(String token) {
        if (token == null) {
            return null;
        }
        return exponentPushTokenRepository.findById(token).orElse(null);
    }

    @Override
    public ExponentPushToken save(ExponentPushToken token) {
        return exponentPushTokenRepository.save(token);
    }

    @Override
    public void delete(ExponentPushToken token) {
        exponentPushTokenRepository.delete(token);
    }
}

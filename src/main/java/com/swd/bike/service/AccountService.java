package com.swd.bike.service;

import com.swd.bike.entity.Account;
import com.swd.bike.repository.AccountRepository;
import com.swd.bike.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "account", key = "#account.id", condition = "#account != null && #account.id != null"),
            @CacheEvict(value = "accounts", allEntries = true),
    })
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Cacheable(value = "account", key = "#id", condition = "#id != null")
    public Account getById(String id) {

        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public String getIdBySubjectId(String subjectId) {
        return accountRepository.findIdBySubjectId(subjectId).orElse(null);
    }

    @Override
    public Account getBySubjectId(String subjectId) {
        return accountRepository.findFirstBySubjectId(subjectId);
    }

    @Override
    public Optional<String> getIdBySubjectIdOpt(String subjectId) {
        return accountRepository.findIdBySubjectId(subjectId);
    }

    @Override
    public Page<Account> getAccountsByFilter(Specification<Account> specification, Pageable pageable) {
        return accountRepository.findAll(specification, pageable);
    }
}
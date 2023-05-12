package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface IAccountService {
    Account save(Account account);

    Account getById(String id);

    String getIdBySubjectId(String subjectId);
    Account getBySubjectId(String subjectId);

    Optional<String> getIdBySubjectIdOpt(String subjectId);

    Page<Account> getAccountsByFilter(Specification<Account> specification, Pageable pageable);
}

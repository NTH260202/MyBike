package com.swd.bike.repository;

import com.swd.bike.entity.Account;
import com.swd.bike.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {
    Page<Account> findAll(Specification<Account> specification, Pageable pageable);

    @Query("SELECT a.id FROM Account a WHERE a.role = ?1")
    List<String> findAllIdsByRoleIs(Role role);

    @Query("SELECT a.id FROM Account a WHERE a.subjectId = ?1")
    Optional<String> findIdBySubjectId(String id);
    List<Account> findDistinctTop5ByRoleAndCreatedAtBetweenOrderByAveragePointDesc(Role role, LocalDateTime from, LocalDateTime to);

    int countAccountsByIsUpdatedAndRoleAndCreatedAtBetween(boolean isUpdated, Role role, LocalDateTime from, LocalDateTime to);
    Account findFirstBySubjectId(String subjectId);

}

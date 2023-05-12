package com.swd.bike.repository;

import com.swd.bike.entity.Post;
import com.swd.bike.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Optional<Post> findFirstByAuthorIdAndStatusAndStartTimeBetween(String authorId, PostStatus status, LocalDateTime from, LocalDateTime to);

    @Query("SELECT post " +
            "FROM Post post " +
            "JOIN post.applications applier " +
            "WHERE applier IS NOT NULL AND applier.id = ?1")
    Page<Post> findByApplicationsContaining(String accountId, Pageable pageable);

    @Modifying
    @Query("UPDATE Post post " +
            "SET post.status = ?1 " +
            "WHERE post IS NOT NULL AND post.startTime < ?2")
    void setPostStatusAfterTime(PostStatus status, LocalDateTime now);

    @Override
    @Query("SELECT p FROM Post p " +
            "LEFT JOIN FETCH p.author " +
            "LEFT JOIN FETCH p.startStation " +
            "LEFT JOIN FETCH p.endStation " +
            "LEFT JOIN FETCH p.applications " +
            "LEFT JOIN FETCH p.trip " +
            "WHERE p.id = :id")
    Optional<Post> findById(@Param("id") Long id);

    List<Post> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "author",
                    "startStation",
                    "endStation"
            }
    )
    Page<Post> findAll(Specification<Post> specification, Pageable pageable);
}

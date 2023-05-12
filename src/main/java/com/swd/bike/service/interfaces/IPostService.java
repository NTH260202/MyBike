package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public interface IPostService {
    boolean isExistWithActiveStation(Specification<Post> specification);
    /**
     * Filter and paging posts
     * @param spec
     * @param pageable
     * @return pageResult
     */
    Page<Post> getAllPosts(Specification<Post> spec, Pageable pageable);

    Post findAnyPostStartAt(Account account, LocalDateTime startTime, Long threshold);

    Post savePost(Post post);

    Post getPost(Long id);

    /**
     * Check if a post still online
     * @param post
     * @return
     */
    boolean isPostActive(Post post);

    boolean isAuthor(Account author, Post post);

    Page<Post> getAppliedPost(Account account, Pageable pageable);

    void scheduleClearExpiredPost(Post post);
}

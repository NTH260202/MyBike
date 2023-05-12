package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.account.response.AccountResponse;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userPost.request.CreatePostRequest;
import com.swd.bike.dto.userPost.request.GetActivePostsRequest;
import com.swd.bike.dto.userPost.request.UpdatePostRequest;
import com.swd.bike.dto.userPost.response.PostDetailResponse;
import com.swd.bike.dto.userPost.response.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "[User] Post Controller", description = "Thao tác với bài đăng")
@RequestMapping("/api/v1/posts")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface IUserPostController {

    @Operation(summary = "Get all my active posts")
    @GetMapping("")
    ResponseEntity<ResponseBase<PageResponse<PostResponse>>> getAllMyActivePost(@ParameterObject GetActivePostsRequest request);

    @Operation(summary = "Create new post. Timezone: UTC ")
    @PostMapping("")
    ResponseEntity<ResponseBase<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest request);

    @GetMapping("/{id}")
    @Operation(summary = "Get post detail")
    ResponseEntity<ResponseBase<PostDetailResponse>> getPost(@PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Update post when Post has no applier.  Timezone: UTC")
    ResponseEntity<ResponseBase<PostResponse>> updatePost(@PathVariable Long id, @Valid @RequestBody UpdatePostRequest request);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post")
    ResponseEntity<ResponseBase<PostResponse>> deletePost(@PathVariable Long id);

    @GetMapping("{id}/appliers")
    @Operation(summary = "Get all appliers")
    ResponseEntity<ResponseBase<ListResponse<AccountResponse>>> getAllAppliers(@PathVariable Long id);

    @PostMapping("/{id}/appliers")
    @Operation(summary = "Apply for a post")
    ResponseEntity<ResponseBase<StatusResponse>> applyPost(@PathVariable Long id);

    @DeleteMapping("/{id}/appliers")
    @Operation(summary = "Applier: Cancel an apply")
    ResponseEntity<ResponseBase<StatusResponse>> cancelApplication(@PathVariable Long id);

    @DeleteMapping("/{id}/appliers/{applierId}")
    @Operation(summary = "Poster: Reject an applier")
    ResponseEntity<ResponseBase<StatusResponse>> rejectApplication(@PathVariable Long id, @PathVariable String applierId);

    @PutMapping("/{id}/appliers/{applierId}")
    @Operation(summary = "Poster: Accept an applier")
    ResponseEntity<ResponseBase<StatusResponse>> acceptApplication(@PathVariable Long id, @PathVariable String applierId);
}
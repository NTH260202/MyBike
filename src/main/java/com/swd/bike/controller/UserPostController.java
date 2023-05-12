package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IUserPostController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.account.response.AccountResponse;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userPost.request.*;
import com.swd.bike.dto.userPost.response.PostDetailResponse;
import com.swd.bike.dto.userPost.response.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPostController extends BaseController implements IUserPostController {

    @Override
    public ResponseEntity<ResponseBase<PageResponse<PostResponse>>> getAllMyActivePost(GetActivePostsRequest request) {
        request.setUserId(getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<PostResponse>> createPost(CreatePostRequest request) {
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<PostDetailResponse>> getPost(Long id) {
        GetPostRequest request = new GetPostRequest();
        request.setPostId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<PostResponse>> updatePost(Long id, UpdatePostRequest request) {
        request.setId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<PostResponse>> deletePost(Long id) {
        DeletePostRequest request = new DeletePostRequest();
        request.setId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<ListResponse<AccountResponse>>> getAllAppliers(Long id) {
        GetAllAppliersRequest request = new GetAllAppliersRequest(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> applyPost(Long id) {
        ApplyPostRequest request = new ApplyPostRequest();
        request.setId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> cancelApplication(Long id) {
        CancelApplicationRequest request = new CancelApplicationRequest();
        request.setPostId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> rejectApplication(Long id, String applierId) {
        RejectApplicationRequest request = new RejectApplicationRequest();
        request.setPostId(id);
        request.setApplierId(applierId);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<StatusResponse>> acceptApplication(Long id, String applierId) {
        AcceptApplicationRequest request = new AcceptApplicationRequest();
        request.setPostId(id);
        request.setApplierId(applierId);
        return this.execute(request);
    }
}

package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IIndexController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.ListResponse;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.index.GetStationsRequest;
import com.swd.bike.dto.userPost.request.GetActivePostsRequest;
import com.swd.bike.dto.userPost.response.PostResponse;
import com.swd.bike.dto.userPost.response.StationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController extends BaseController implements IIndexController {
    @Override
    public ResponseEntity<ResponseBase<PageResponse<PostResponse>>> getAllIndexPosts(GetActivePostsRequest request) {
        request.setExceptUserId(getUserId());
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<ListResponse<StationResponse>>> getAllStations(GetStationsRequest request) {
        return this.execute(request);
    }

}

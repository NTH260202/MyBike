package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IMediaController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.media.request.UploadFileRequest;
import com.swd.bike.dto.media.response.UploadFileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaController extends BaseController implements IMediaController {
    @Override
    public ResponseEntity<ResponseBase<UploadFileResponse>> uploadFile(UploadFileRequest request) {
        return this.execute(request);
    }
}

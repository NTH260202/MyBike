package com.swd.bike.handler.media;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.media.request.UploadFileRequest;
import com.swd.bike.dto.media.response.UploadFileResponse;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.interfaces.IMediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class UploadFileHandler extends RequestHandler<UploadFileRequest, UploadFileResponse> {

    private final IMediaService mediaService;

    @Override

    public UploadFileResponse handle(UploadFileRequest request) {
        MultipartFile file = request.getFile();
        if (file == null || file.isEmpty()) {
            throw new InternalException(ResponseCode.FAILED);
        }
        String uploadedPath = mediaService.uploadFile(file);
        if (StringUtils.isNotBlank(uploadedPath)) {
            return new UploadFileResponse(uploadedPath);
        }
        throw new InternalException(ResponseCode.FAILED);
    }
}

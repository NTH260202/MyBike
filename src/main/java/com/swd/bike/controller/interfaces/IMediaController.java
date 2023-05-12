package com.swd.bike.controller.interfaces;

import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.media.request.UploadFileRequest;
import com.swd.bike.dto.media.response.UploadFileResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api/v1/medias")
@Tag(name = "Media Controller", description = "Thao tác với media")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface IMediaController {
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResponseBase<UploadFileResponse>> uploadFile(@Valid @ModelAttribute UploadFileRequest request);
}

package com.swd.bike.dto.media.request;

import com.swd.bike.core.BaseRequestData;
import com.swd.bike.validation.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileRequest extends BaseRequestData {
    @NotNull
    @File(maxSizeInKB = 20_000, message = "File size must be <= 20MB")
    private MultipartFile file;
}

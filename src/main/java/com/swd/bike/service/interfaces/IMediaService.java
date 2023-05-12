package com.swd.bike.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {
    /**
     * Upload file to storage
     * @param file
     * @return path to file
     */
    String uploadFile(MultipartFile file);
}

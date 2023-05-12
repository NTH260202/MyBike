package com.swd.bike.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.swd.bike.config.AwsConfig;
import com.swd.bike.service.interfaces.IMediaService;
import com.swd.bike.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService implements IMediaService {

    private final AwsConfig awsConfig;

    @Override
    public String uploadFile(MultipartFile file) {
        String filePath = "";
        try {
            File f = FileUtils.convertMultipartFileToFile(file);
            String fileName = FileUtils.generateUniqueFileName(file);
            filePath = "/" + awsConfig.bucketName + "/" + fileName;
            uploadFile(fileName, f);
            f.delete();
            return filePath;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private void uploadFile(String fileName, File file) {
        awsConfig.getS3Client().putObject(new PutObjectRequest(awsConfig.bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}

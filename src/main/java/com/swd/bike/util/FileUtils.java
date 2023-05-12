package com.swd.bike.util;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class FileUtils {

    @SneakyThrows
    public static File convertMultipartFileToFile(MultipartFile multipartFile) {
        File cnvFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(cnvFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return cnvFile;
    }

    public static String generateUniqueFileName(MultipartFile multipartFile) {
        try {
            return new Date().getTime() + multipartFile.getOriginalFilename().replace(" ", "_");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Date().getTime() + "";
    }
}

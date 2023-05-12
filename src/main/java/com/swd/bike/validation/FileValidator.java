package com.swd.bike.validation;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class FileValidator implements ConstraintValidator<File, MultipartFile> {

    private static final Integer KB = 1024;

    private long maxSizeInKB;

    private String[] acceptedExtensions;

    @Override
    public void initialize(File constraintAnnotation) {
        this.maxSizeInKB = constraintAnnotation.maxSizeInKB();
        this.acceptedExtensions = constraintAnnotation.acceptedExtensions();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        boolean isFileSizeValid = maxSizeInKB == 0 || file.getSize() <= maxSizeInKB * KB;

        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        boolean isFileExtensionValid = acceptedExtensions.length == 0 ||
                Arrays.stream(acceptedExtensions).anyMatch(ext -> ext != null && ext.equalsIgnoreCase(fileExtension));

        return isFileSizeValid && isFileExtensionValid;
    }
}

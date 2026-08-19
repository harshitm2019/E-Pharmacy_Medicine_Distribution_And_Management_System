package com.harshit.pharmacy.common.validator;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.config.FileProperties;
import com.harshit.pharmacy.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileValidator {


    private final FileProperties fileProperties;

    public void validatePrescription(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException(ErrorMessages.PRESCRIPTION_FILE_REQUIRED);
        }

        long maxSizeBytes = fileProperties.maxSizeBytes();

        if (file.getSize() > maxSizeBytes) {
            throw new BadRequestException(ErrorMessages.PRESCRIPTION_FILE_SIZE_EXCEEDED + fileProperties.maxSize());
        }

        String contentType = file.getContentType();

        if (!fileProperties.isAllowedType(contentType)) {
            throw new BadRequestException(ErrorMessages.INVALID_PRESCRIPTION_FILE_TYPE);
        }
    }
}

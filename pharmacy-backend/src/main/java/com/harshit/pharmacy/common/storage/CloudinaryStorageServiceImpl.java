package com.harshit.pharmacy.common.storage;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryStorageServiceImpl implements StorageService{

    private final Cloudinary cloudinary;


    @Override
    public String uploadPrescription(MultipartFile file) {

        try {

            String extension = extractExtension(file.getOriginalFilename());
            String publicId = generateUniquePublicId(file.getOriginalFilename());

            Map<?, ?> response = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "prescriptions",
                            "public_id", publicId,
                            "resource_type", "auto",
                            "format", extension,
                            "type", "upload"
                    )
            );
            return response.get("public_id").toString();

        } catch (IOException ex) {

              throw new BadRequestException(ErrorMessages.UNABLE_TO_UPLOAD_PRESCRIPTION);

        }

    }

    @Override
    public String getFileUrl(String filePath) {

        String pathWithExtension = filePath.endsWith(".pdf") ? filePath : filePath + ".pdf";

        return cloudinary.url()
                .secure(true)
                .resourceType("image")
                .generate(filePath);
    }

    @Override
    public void deletePrescription(String filePath) {

        try {

            String cleanPublicId = stripExtension(filePath);
            cloudinary.uploader().destroy(cleanPublicId, ObjectUtils.asMap("resource_type", "image"));

        } catch (IOException ex) {

            throw new BadRequestException(ErrorMessages.UNABLE_TO_DELETE_PRESCRIPTION);

        }
    }

    private String generateUniquePublicId(String originalFilename) {
        String baseName = "prescription";

        if (StringUtils.hasText(originalFilename)) {
            int lastDotIndex = originalFilename.lastIndexOf('.');
            baseName = (lastDotIndex > 0) ? originalFilename.substring(0, lastDotIndex) : originalFilename;
            baseName = baseName.replaceAll("[^a-zA-Z0-9_-]", "_");
        }

        String suffix = UUID.randomUUID().toString().substring(0, 6);
        return baseName + "_" + suffix;
    }

    private String extractExtension(String originalFilename) {
        if (StringUtils.hasText(originalFilename)) {
            int lastDotIndex = originalFilename.lastIndexOf('.');
            if (lastDotIndex > 0) {
                String ext = originalFilename.substring(lastDotIndex + 1).toLowerCase();
                return "pdf".equals(ext) ? "pdf" : "jpg";
            }
        }
        return "jpg";
    }

    private String stripExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        return (lastDotIndex > 0) ? filePath.substring(0, lastDotIndex) : filePath;
    }


}




package com.harshit.pharmacy.common.storage;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryStorageServiceImpl implements StorageService{

    private final Cloudinary cloudinary;


    @Override
    public String uploadPrescription(MultipartFile file) {

        try {

            Map<?, ?> response = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "prescriptions",
                            "resource_type", "raw",
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

        return cloudinary.url()
                .secure(true)
                .resourceType("raw")
                .generate(filePath);

    }

    @Override
    public void deletePrescription(String filePath) {

        try {

            cloudinary.uploader().destroy(filePath, ObjectUtils.asMap("resource_type", "raw"));

        } catch (IOException ex) {

            throw new BadRequestException(ErrorMessages.UNABLE_TO_DELETE_PRESCRIPTION);

        }
    }


    }




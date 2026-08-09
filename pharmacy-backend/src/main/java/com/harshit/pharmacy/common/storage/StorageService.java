package com.harshit.pharmacy.common.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String uploadPrescription(MultipartFile file);

    String getFileUrl(String filePath);

    void deletePrescription(String filePath);

}

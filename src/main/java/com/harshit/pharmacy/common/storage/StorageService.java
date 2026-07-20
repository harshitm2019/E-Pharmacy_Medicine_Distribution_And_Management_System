package com.harshit.pharmacy.common.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String uploadPrescription(MultipartFile file);

    byte[] downloadPrescription(String filePath);

    void deletePrescription(String filePath);

}

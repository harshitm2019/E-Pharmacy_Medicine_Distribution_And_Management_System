package com.harshit.pharmacy.common.storage;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GcpStorageServiceImpl implements StorageService{


    @Override
    public String uploadPrescription(MultipartFile file) {
        return "";
    }

    @Override
    public byte[] downloadPrescription(String filePath) {
        return new byte[0];
    }

    @Override
    public void deletePrescription(String filePath) {

    }
}

package com.shield.eaarogya.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    // --------------------------------- Upload a file to AWS S3 -----------------------------------------------------
    String uploadFile(MultipartFile multipartFile, long patientId);

    // ------------------------------------ View all the files of S3 -------------------------------------------------
    List<String> allFilesS3(String patientId);

    // --------------------------------------- Download file using filename ------------------------------------------
    byte[] downloadFile(String fileName);

    // ------------------------------------ Delete file from S3 ------------------------------------------------------
    String deleteFile(String fileName);

    // ------------------------------------ Delete all file from S3 for that particular patient ----------------------------------------------------
    String deleteAllFiles(String patientId);
}

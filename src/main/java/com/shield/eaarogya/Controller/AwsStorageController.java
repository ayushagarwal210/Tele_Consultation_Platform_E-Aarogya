package com.shield.eaarogya.Controller;

import com.shield.eaarogya.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/fileaws")
public class AwsStorageController {

    @Autowired
    private StorageService storageService;

    // ------------------------------- Upload a file to AWS S3 ------------------------------------------------------
    // ONLY ALLOW PDF FILES TO UPLOAD
//    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping("/uploadFile/{patientId}")
    public String uploadFile(@RequestParam(value = "file")MultipartFile multipartFile, @PathVariable String patientId) {
        System.out.println("Inside API to upload file");
        if(multipartFile.isEmpty())
            return "File is empty";
        return storageService.uploadFile(multipartFile, Long.parseLong(patientId));
    }

    // ------------------------------------- Get list of all the files available on patient side ---------------------------------------
    // On the doctor's end, when the patient uploads any document,
    // whenever doctor clicks on the refresh button of the component
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/getAllFilesPatient/{patientId}")
    public List<String> allFilesPatientS3(@PathVariable String patientId) {
        return storageService.allFilesPatientS3(patientId);
    }

    // ---------------------------- Get list of all files on doctor's side --------------------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/getAllFilesDoctor/{patientId}")
    public List<String> allFilesDoctorS3(@PathVariable String patientId) {
        return storageService.allFilesDoctorS3(patientId);
    }

    // ------------------------------------- Delete File from S3 -------------------------------------------------------
    // Whenever patient wants to delete the uploaded file, he can easily do that using this API
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @DeleteMapping("/deleteFile/{fileName}")
    public String deleteFile(@PathVariable String fileName) {
        return storageService.deleteFile(fileName);
    }

    // ------------------------------------- Download File from S3 -----------------------------------------------------
    // Whenever the doctor clicks on any file name, that file name should be passed here in the API and the pdf will be opened in the next tab
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition",
                "inline;filename=" + fileName);

        return ResponseEntity
                .status(HTTP_OK)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(httpHeaders)
                .body(storageService.downloadFile(fileName));
    }

    // ----------------------------------------- Flush the S3 of that particular patient ---------------------------------------------------------
    // When doctor is done with the consultation and he ends the prescription, this API should be invoked
    // It is now kept in the add prescription API implementation
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @DeleteMapping("/deleteAllFiles/{patientId}")
    public String deleteAllFiles(@PathVariable String patientId) {
        return storageService.deleteAllFiles(patientId);
    }
}

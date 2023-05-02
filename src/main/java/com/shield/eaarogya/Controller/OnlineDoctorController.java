package com.shield.eaarogya.Controller;

import com.shield.eaarogya.Service.OnlineDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/OnlineDoctors")
public class OnlineDoctorController {

    @Autowired
    private OnlineDoctorService onlineDoctorService;

    // ----------------------- Get count of online doctors -------------------
    @GetMapping("/totalOnline")
    public int totalOnlineDoctors() {
        return onlineDoctorService.totalDoctorsOnline();
    }
}

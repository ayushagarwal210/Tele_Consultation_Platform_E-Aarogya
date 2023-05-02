package com.shield.eaarogya.Controller;

import com.shield.eaarogya.DTO.DateWiseConsultations;
import com.shield.eaarogya.Service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/consultation")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    // ------------------------------- Total count of consultations for Home Page --------------------------------------
    @GetMapping("/getAllConsultationsCount")
    public long getAllConsultaionCount() {
        return consultationService.getAllConsultationsCount();
    }

    // ----------------------------- Total consultations date-wise for home page ------------------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/totalDateWiseConsultations")
    public List<DateWiseConsultations> totalDateWiseConsultations() {
        return consultationService.totalDateWiseConsultations();
    }

    // ------------------------ Date-wise Consultation for doctor dashboard --------------------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/totalDateWiseConsultations/{doctorId}")
    public List<DateWiseConsultations> totalDateWiseConsultations(@PathVariable String doctorId) {
        return consultationService.totalDateWiseConsultations(Long.parseLong(doctorId));
    }

    // ------------------------- Total consultations done by doctor ------------------------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/totalConsultationByDoctor/{doctorId}")
    public long totalConsultationByDoctor(@PathVariable String doctorId) {
        return consultationService.totalConsultationByDoctor(Long.parseLong(doctorId));
    }

    // ------------------------- Total daily consultations done by doctor ------------------------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/totalDailyConsultationByDoctor/{doctorId}")
    public long totalDailyConsultationByDoctor(@PathVariable String doctorId) {
        return consultationService.totalDailyConsultationByDoctor(Long.parseLong(doctorId));
    }

    // ------------------------- Total consultations done by patient Id ------------------------------------------------
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/totalConsultationByPatientId/{patientId}")
    public long totalConsultationByPatient(@PathVariable String patientId) {
        return consultationService.totalConsultationByPatient(Long.parseLong(patientId));
    }
}

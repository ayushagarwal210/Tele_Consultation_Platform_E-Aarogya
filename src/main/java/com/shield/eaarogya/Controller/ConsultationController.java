package com.shield.eaarogya.Controller;

import com.shield.eaarogya.DTO.DateWiseConsultations;
import com.shield.eaarogya.Service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    @GetMapping("/totalDateWiseConsultations")
    public List<DateWiseConsultations> totalDateWiseConsultations() {
        return consultationService.totalDateWiseConsultations();
    }

    // ------------------------ Date-wise Consultation for doctor dashboard --------------------------------------------
    @GetMapping("/totalDateWiseConsultations/{doctorId}")
    public List<DateWiseConsultations> totalDateWiseConsultations(@PathVariable String doctorId) {
        return consultationService.totalDateWiseConsultations(Long.parseLong(doctorId));
    }

    // ------------------------- Total consultations done by doctor ------------------------------------------------
    @GetMapping("/totalConsultationByDoctor/{doctorId}")
    public long totalConsultationByDoctor(@PathVariable String doctorId) {
        return consultationService.totalConsultationByDoctor(Long.parseLong(doctorId));
    }

    // ------------------------- Total daily consultations done by doctor ------------------------------------------------
    @GetMapping("/totalDailyConsultationByDoctor/{doctorId}")
    public long totalDailyConsultationByDoctor(@PathVariable String doctorId) {
        return consultationService.totalDailyConsultationByDoctor(Long.parseLong(doctorId));
    }

    // ------------------------- Total consultations done by patient Id ------------------------------------------------
    @GetMapping("/totalConsultationByPatientId/{patientId}")
    public long totalConsultationByPatient(@PathVariable String patientId) {
        return consultationService.totalConsultationByPatient(Long.parseLong(patientId));
    }
}

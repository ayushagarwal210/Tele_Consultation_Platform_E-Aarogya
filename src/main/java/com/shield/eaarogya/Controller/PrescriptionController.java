package com.shield.eaarogya.Controller;

import com.shield.eaarogya.DTO.DailyLogDetails;
import com.shield.eaarogya.DTO.FollowUpDetails;
import com.shield.eaarogya.DTO.PrescriptionDetails;
import com.shield.eaarogya.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    PrescriptionService prescriptionService;

    // ------------------------------ Getting All the Prescriptions of particular patient by Patient ---------------------------------
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/getPrescriptionsPatient/{patientId}")
    public List<PrescriptionDetails> getPrescriptionsPatient(@PathVariable String patientId) {
        return this.prescriptionService.getPrescriptionsPatient(Long.parseLong(patientId));
    }

    // ------------------------------ Getting All the Prescriptions of particular patient by Doctor ---------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/getPrescriptionsDoctor/{patientId}")
    public List<PrescriptionDetails> getPrescriptionsDoctor(@PathVariable String patientId) {
        return this.prescriptionService.getPrescriptionsDoctor(Long.parseLong(patientId));
    }

    // -------------------------------------- Adding Prescription to the Database ----------------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping("/addPrescription")
    public PrescriptionDetails addPrescription(@RequestBody PrescriptionDetails prescriptionDetails) {
        return this.prescriptionService.addPrescription(prescriptionDetails);
    }

}

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
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    PrescriptionService prescriptionService;

    // ------------------------------ Getting All the Prescriptions of particular patient ---------------------------------
//    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/getPrescriptions/{patientId}")
    public List<PrescriptionDetails> getPrescriptions(@PathVariable String patientId) {
        return this.prescriptionService.getPrescriptions(Long.parseLong(patientId));
    }

    // -------------------------------------- Adding Prescription to the Database ----------------------------------------
//    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping("/addPrescription")
    public PrescriptionDetails addPrescription(@RequestBody PrescriptionDetails prescriptionDetails) {
        return this.prescriptionService.addPrescription(prescriptionDetails);
    }

}

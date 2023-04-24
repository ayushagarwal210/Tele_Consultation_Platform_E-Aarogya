package com.shield.eaarogya.Controller;

import com.shield.eaarogya.DTO.DailyLogDetails;
import com.shield.eaarogya.DTO.FollowUpDetails;
import com.shield.eaarogya.DTO.PrescriptionDetails;
import com.shield.eaarogya.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    PrescriptionService prescriptionService;

    // ------------------------------ Getting All the Prescriptions of particular patient ---------------------------------
    @GetMapping("/getPrescriptions/{patientId}")
    public List<PrescriptionDetails> getPrescriptions(@PathVariable String patientId) {
        return this.prescriptionService.getPrescriptions(Long.parseLong(patientId));
    }

    // ---------------------------------------- Getting All the Prescriptions ------------------------------------------
    @GetMapping("/getAllPrescriptions")
    public List<PrescriptionDetails> getAllPrescriptions() {
        return this.prescriptionService.getAllPrescriptions();
    }

    // -------------------------------------- Adding Prescription to the Database ----------------------------------------
    @PostMapping("/addPrescription")
    public PrescriptionDetails addPrescription(@RequestBody PrescriptionDetails prescriptionDetails) {
        return this.prescriptionService.addPrescription(prescriptionDetails);
    }

    // -------------------------------------- Getting Prescription based on Prescription Id ---------------------------------
    @GetMapping("/getPrescriptionById/{pres_id}")
    public PrescriptionDetails getPrescriptionById(@PathVariable String pres_id) {
        return this.prescriptionService.getPrescriptionById(Integer.parseInt(pres_id));
    }
}

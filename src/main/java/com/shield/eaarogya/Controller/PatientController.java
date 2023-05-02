package com.shield.eaarogya.Controller;

import com.shield.eaarogya.DTO.FollowUpDetails;
import com.shield.eaarogya.Entity.Patient;
import com.shield.eaarogya.Service.PatientService;
import com.shield.eaarogya.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    PrescriptionService prescriptionService;

    // -------------------------------- Get List of all Patients --------------------------------
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/getPatients")
    public List<Patient> getPatients() {
        return this.patientService.getPatient();
    }

    // -------------------------------- Adding Patient to database -------------------------------
    @PostMapping("/addPatient")
    public Patient addPatient(@RequestBody Patient patient) {
        return this.patientService.addPatient(patient);
    }

    // ------------------------------- Get Patient by Patient Id ------------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/getPatientById/{patientId}")
    public Patient getPatientById(@PathVariable String patientId) {
        return patientService.getPatientByPatientId(Long.parseLong(patientId));
    }

    // ------------------------------- Get Patient details by patient email --------------------------------------
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/getpatientByEmail/{email}")
    public Patient getPatientByEmail(@PathVariable String email) {
//        String email = loginCredentials.getEmail();
        return patientService.findByEmail(email);
    }

    // ------------------------------ Get Patient from Phone Number --------------------------------------
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/getPatientByPhoneNumber/{phoneNumber}")
    public Patient getPatientByPhoneNumber(@PathVariable String phoneNumber) {
        return patientService.getPatientByPhoneNumber(phoneNumber);
    }

    // ----------------------------- Update Patient by Patient Id ----------------------------------------
    // (You have to send every details to the API)
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PutMapping("/updatePatient/{patientId}")
    public Patient updatePatient(@RequestBody Patient patient, @PathVariable String patientId) {
        return patientService.updatePatient(patient, Long.parseLong(patientId));
    }

    // --------------------------------- Get Patient by patient Id ----------------------------------------------
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("getPatient/{patientId}")
    public Patient getPatient(@PathVariable String patientId) {
        return patientService.getPatientByPatientId(Long.parseLong(patientId));
    }

    // Fetch follow-up dates with prescription details, if the prescription contain follow-up date for that particular patient.
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/getFollowUp/{patient_id}")
    public List<FollowUpDetails> getFollowUpDetails(@PathVariable String patient_id) {
        return prescriptionService.getFollowUpDetails(Long.parseLong(patient_id));
    }
}

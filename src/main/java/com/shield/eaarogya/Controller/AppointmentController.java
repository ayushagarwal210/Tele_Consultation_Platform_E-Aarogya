package com.shield.eaarogya.Controller;

import com.shield.eaarogya.DTO.AppointmentDetails;
import com.shield.eaarogya.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    // -------------------------------------- Request an Appointment ------------------------------------------
//    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping("/requestAppointment")
    public long requestAppointment(@RequestBody AppointmentDetails appointmentDetails) {

        return appointmentService.requestAppointment(appointmentDetails);
    }

    // ------------------------------------- Get list of all Appointments -------------------------------

    @GetMapping("/getAllAppointments")
    public List<AppointmentDetails> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // ------------------------------------ Delete a Particular Appointment ----------------------------------
    // This API is used by doctor
//    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @DeleteMapping("/deleteAppointment/{appointmentId}")
    public boolean deleteAppointment(@PathVariable String appointmentId) {
        return appointmentService.deleteAppointment(Long.parseLong(appointmentId));
    }

    // -------------------------- Delete a Particular Appointment based on patient Id----------------------------
    // This API is used by patient
//    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @DeleteMapping("/deleteAppointmentByPatientId/{patientId}")
    public boolean deleteAppointmentByPatientId(@PathVariable String patientId) {
        return appointmentService.deleteAppointmentByPatientId(Long.parseLong(patientId));
    }

    // -------------------------------- Patients waiting based on appointment Id ---------------------------------------------
//    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/waitingPatients/{appointmentId}")
    public int waitingPatients(@PathVariable long appointmentId) {
        return appointmentService.waitingPatients(appointmentId);
    }

    // -------------------------- Get List of all Appointments for particular department ----------------------
    @GetMapping("/getAllAppointments/{departmentName}")
    public List<AppointmentDetails> getAllAppointments(@PathVariable String departmentName) {
        return appointmentService.getAppointmentsByDepartment(departmentName);
    }

    // ---------------------- Check if any appointment exist in database or not for a patient -------------------
//    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/checkAppointments/{patientId}")
    public boolean checkForAppointments(@PathVariable String patientId) {
        return appointmentService.checkAppointment(Long.parseLong(patientId));
    }
}

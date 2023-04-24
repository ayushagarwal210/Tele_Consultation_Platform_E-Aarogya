package com.shield.eaarogya.Controller;

import com.shield.eaarogya.DTO.AppointmentDetails;
import com.shield.eaarogya.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;
    // -------------------------------------- Request an Appointment ------------------------------------------
    @PostMapping("/requestAppointment")
    public long requestAppointment(@RequestBody AppointmentDetails appointmentDetails) {

        return appointmentService.requestAppointment(appointmentDetails);
    }

    // ------------------------------------- Get list of all Appointments -------------------------------
    @GetMapping("/getAllAppointments")
    public List<AppointmentDetails> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // --------------------------------------- Delete a Particular Appointment ----------------------------------
    @DeleteMapping("/deleteAppointment/{appointmentId}")
    public boolean deleteAppointment(@PathVariable String appointmentId) {
        return appointmentService.deleteAppointment(Long.parseLong(appointmentId));
    }

    // -------------------------- Delete a Particular Appointment based on patient Id----------------------------
    @DeleteMapping("/deleteAppointmentByPatientId/{patientId}")
    public boolean deleteAppointmentByPatientId(@PathVariable String patientId) {
        return appointmentService.deleteAppointmentByPatientId(Long.parseLong(patientId));
    }

    // -------------------------------- Patients waiting based on appointment Id ---------------------------------------------
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
    @GetMapping("/checkAppointments/{patientId}")
    public boolean checkForAppointments(@PathVariable String patientId) {
        return appointmentService.checkAppointment(Long.parseLong(patientId));
    }
}

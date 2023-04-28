package com.shield.eaarogya.Service;

import com.shield.eaarogya.DTO.AppointmentDetails;

import java.util.List;

public interface AppointmentService {

    // ------------------------------------- request Appointment ---------------------------------------
    long requestAppointment(AppointmentDetails appointmentDetails);

    // ------------------------------------- Get list of all Appointments -------------------------------
    List<AppointmentDetails> getAllAppointments();

    // --------------------------------------- Appointment accepted ----------------------------------------------------
    boolean appointmentAccepted(long appointmentId);

    // ----------------------------- Check if appointment is accepted or not -------------------------------------------
    boolean isAppointmentAccepted(long patientId);

    // --------------------------- Delete Appointment Status using patient Id -----------------------------------------
    boolean deleteAppointmentStatus(long patientId);

    // -------------------------------------- Delete a specific Appointment --------------------------------------
    boolean deleteAppointment(long appointmentId);

    // -------------------------------------- Delete a specific Appointment --------------------------------------
    boolean deleteAppointmentByPatientId(long patientId);

    // ---------------------- Get list of all waiting patients (Queue) for a particular department -----------------
    int waitingPatients(long appointment);

    // ----------------------- Get list of all appointments for a particular department ---------------------------
    List<AppointmentDetails> getAppointmentsByDepartment(String departmentName);

    // ------------------------ Check if any appointment is present for a patient ---------------------------------
    boolean checkAppointment(long patientId);

    // --------- Get all the appointments for a particular department and the language speak by doctor -------------
    List<AppointmentDetails> getAppointmentByPreferredLanguageAndDepartmentName(long doctorId);
}

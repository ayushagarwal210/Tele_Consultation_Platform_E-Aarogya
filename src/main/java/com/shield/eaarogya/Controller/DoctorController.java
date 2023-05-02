package com.shield.eaarogya.Controller;

import com.shield.eaarogya.DTO.AppointmentDetails;
import com.shield.eaarogya.DTO.DailyLogDetails;
import com.shield.eaarogya.DTO.DoctorDetails;
import com.shield.eaarogya.Service.AppointmentService;
import com.shield.eaarogya.Service.DoctorService;
import com.shield.eaarogya.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private AppointmentService appointmentService;

    // ----------------------------------------- Register a Doctor -----------------------------------------
    @PostMapping("/registerDoctor")
    public DoctorDetails registerDoctor(@RequestBody DoctorDetails doctorDetails) {
        return doctorService.addDoctor(doctorDetails);
    }

    // ------------------------------------- Get List of all Doctors ------------------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/getAllDoctors")
    public List<DoctorDetails> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // ---------------------------------------- Get Doctor by EmailId -------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/getdoctorByEmail/{email}")
    public DoctorDetails getDoctorByEmail(@PathVariable String email) {
//        String email = loginCredentials.getEmail();

        return doctorService.findByEmail(email);
    }

    // ------------------------------------------ Get Doctor from Phone Number --------------------------------------
     @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/getDoctorByPhoneNumber/{phoneNumber}")
    public DoctorDetails getDoctorByPhoneNumber(@PathVariable String phoneNumber) {

        // When otp is verified the doctor details are fetched and sent to the front-end using this API, also this will set the isOnline doctor to TRUE.
        return doctorService.getDoctorByPhoneNumber(phoneNumber);
    }

    // ---------------------------------- Fetch Doctor's Daily Log based on doctor id ---------------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/doctorDailyLog/{doctorId}")
    public List<DailyLogDetails> doctorDailyLog(@PathVariable String doctorId) {
        return prescriptionService.doctorDailyLog(Long.parseLong(doctorId));
    }

    // -------------- Get list of all Appointments with department and language spoken by doctor ------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/appointmentList/{doctorId}")
    public List<AppointmentDetails> getAppointmentByPreferredLanguageAndDepartmentName(@PathVariable String doctorId) {
        return appointmentService.getAppointmentByPreferredLanguageAndDepartmentName(
                Long.parseLong(doctorId)
        );
    }

    // -------------------------------------- Update Doctor Profile ---------------------------------------
    // (You have to send every details to the API)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping("/updateDoctor/{doctorId}")
    public DoctorDetails updateDoctor(@RequestBody DoctorDetails doctorDetails, @PathVariable String doctorId) {
        return doctorService.updateDoctor(doctorDetails, Long.parseLong(doctorId));
    }

    // ------------------------ Logout the doctor and update the isOnline doctor to FALSE --------------------------
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping("/Doctorlogout/{doctorId}")
    public boolean doctorLogout(@PathVariable Long doctorId) {
        return doctorService.doctorLogout(doctorId);
    }
}

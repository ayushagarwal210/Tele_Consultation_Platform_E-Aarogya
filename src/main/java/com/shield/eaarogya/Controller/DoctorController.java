package com.shield.eaarogya.Controller;

import com.shield.eaarogya.DTO.AppointmentDetails;
import com.shield.eaarogya.DTO.DailyLogDetails;
import com.shield.eaarogya.DTO.DoctorDetails;
import com.shield.eaarogya.Service.AppointmentService;
import com.shield.eaarogya.Service.DoctorService;
import com.shield.eaarogya.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
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
    @GetMapping("/getAllDoctors")
    public List<DoctorDetails> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // ---------------------------------------- Get Doctor by EmailId -------------------------------
    @GetMapping("/getdoctorByEmail/{email}")
    public DoctorDetails getDoctorByEmail(@PathVariable String email) {
//        String email = loginCredentials.getEmail();

        return doctorService.findByEmail(email);
    }

    // ------------------------------------------ Get Doctor from Phone Number --------------------------------------
    @GetMapping("/getDoctorByPhoneNumber/{phoneNumber}")
    public DoctorDetails getDoctorByPhoneNumber(@PathVariable String phoneNumber) {

        // When otp is verified the doctor details are fetched and sent to the front-end using this API, also this will set the isOnline doctor to TRUE.
        return doctorService.getDoctorByPhoneNumber(phoneNumber);
    }

    // ---------------------------------- Fetch Doctor's Daily Log based on doctor id ---------------------------------
    @GetMapping("/doctorDailyLog/{doctorId}")
    public List<DailyLogDetails> doctorDailyLog(@PathVariable String doctorId) {
        return prescriptionService.doctorDailyLog(Long.parseLong(doctorId));
    }

    // -------------- Get list of all Appointments with department and language spoken by doctor ------------------
    @GetMapping("/appointmentList/{doctorId}")
    public List<AppointmentDetails> getAppointmentByPreferredLanguageAndDepartmentName(@PathVariable String doctorId) {
        return appointmentService.getAppointmentByPreferredLanguageAndDepartmentName(
                Long.parseLong(doctorId)
        );
    }

    // ------------------------ Logout the doctor and update the isOnline doctor to FALSE --------------------------
    @PutMapping("/Doctorlogout/{doctorId}")
    public boolean doctorLogout(@PathVariable Long doctorId) {
        return doctorService.doctorLogout(doctorId);
    }
}

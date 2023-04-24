package com.shield.eaarogya.Service;

import com.shield.eaarogya.DTO.DoctorDetails;

import java.util.List;

public interface DoctorService {

    DoctorDetails addDoctor(DoctorDetails doctorDetails);

    List<DoctorDetails> getAllDoctors();

    DoctorDetails findByEmail(String email);

    DoctorDetails getDoctorByPhoneNumber(String phoneNumber);

    boolean doctorLogout(long doctorId);

    DoctorDetails updateDoctor(DoctorDetails doctorDetails, long doctorId);
}

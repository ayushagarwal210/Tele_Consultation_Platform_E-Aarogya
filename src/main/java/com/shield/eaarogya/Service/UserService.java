package com.shield.eaarogya.Service;


import com.shield.eaarogya.Entity.Doctor;
import com.shield.eaarogya.Entity.Patient;
import com.shield.eaarogya.Repository.DoctorRepository;
import com.shield.eaarogya.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private Set getAuthorityPatient(){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_PATIENT"));
        return authorities;
    }

    private Set getAuthorityDoctor(){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
        return authorities;
    }
    @Override
    public UserDetails loadUserByUsername(String phoneNumber){
        // Check if user is a doctor
        Doctor doctor = doctorRepository.findByPhoneNumber(phoneNumber);
        if (doctor != null) {
            return new User(doctor.getPhoneNumber(), doctor.getPhoneNumber(), getAuthorityDoctor());
        }

        // Check if user is a patient
        Patient patient = patientRepository.findByPhoneNo(phoneNumber);
        if (patient != null) {
            return new User(patient.getPhoneNo(), patient.getPhoneNo(), getAuthorityPatient());
        }

        throw new UsernameNotFoundException("User Not Found");
    }
}

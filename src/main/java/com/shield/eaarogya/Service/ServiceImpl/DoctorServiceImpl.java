package com.shield.eaarogya.Service.ServiceImpl;

import com.shield.eaarogya.DTO.DoctorDetails;
import com.shield.eaarogya.Entity.Department;
import com.shield.eaarogya.Entity.Doctor;
import com.shield.eaarogya.Entity.OnlineDoctor;
import com.shield.eaarogya.Repository.DoctorRepository;
import com.shield.eaarogya.Repository.OnlineDoctorRepository;
import com.shield.eaarogya.Service.DepartmentService;
import com.shield.eaarogya.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private OnlineDoctorRepository onlineDoctorRepository;

    // --------------------------------- Add Doctor to Database -------------------------------------
    @Transactional
    @Override
    public DoctorDetails addDoctor(DoctorDetails doctorDetails) {

        try {
            Department department = departmentService.getDepartmentByName(doctorDetails.getDepartmentName());
            Set<String> languagesSpeak = doctorDetails.getDoctorLanguages();
            Set<String> languages = new HashSet<>();

            for(String s: languagesSpeak)
                languages.add(s.toLowerCase());

            Doctor doctor = new Doctor(doctorDetails.getTitle(),
                    doctorDetails.getFirstName(), doctorDetails.getLastName(),
                    doctorDetails.getPhoneNumber(),
                    doctorDetails.getEmail(), doctorDetails.getRegistration_number(),
                    doctorDetails.getDob(), doctorDetails.getGender(),
                    languages,
                    doctorDetails.getAddr(), doctorDetails.getCity(),
                    doctorDetails.getPincode(), department);

            doctorRepository.save(doctor);
            // Saving the online doctors to the database
            onlineDoctorRepository.save(new OnlineDoctor(
                    false,
                    doctor
            ));
            return doctorDetails;
        } catch (Exception e) {
            System.out.println("Error Occurred while adding doctor to the database");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DoctorDetails> getAllDoctors() {

        try {
            List<Doctor> doctorList = doctorRepository.findAll();

            List<DoctorDetails> doctorDetailsList = new ArrayList<>();

            for (Doctor doctor : doctorList) {
                doctorDetailsList.add(new DoctorDetails(doctor.getDoctorId(),
                        doctor.getTitle(), doctor.getFirstName(),
                        doctor.getLastName(), doctor.getEmail(),
                        doctor.getPhoneNumber(),
                        doctor.getRegistration_number(), doctor.getDob(),
                        doctor.getGender(), doctor.getAddr(), doctor.getCity(),
                        doctor.getPincode(), doctor.getDepartment().getDepartmentName(),
                        doctor.getDoctorLanguages()));
            }

            return doctorDetailsList;
        } catch (Exception e) {
            System.out.println("Error Occurred while getting list of all doctors");
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------- Fetch doctor by Email --------------------------------------------
    @Override
    public DoctorDetails findByEmail(String email) {
        try {
            Doctor doctor = doctorRepository.findByEmail(email);

            return new DoctorDetails(doctor.getDoctorId(),
                    doctor.getTitle(), doctor.getFirstName(),
                    doctor.getLastName(), doctor.getEmail(),
                    doctor.getPhoneNumber(),
                    doctor.getRegistration_number(),
                    doctor.getDob(), doctor.getGender(),
                    doctor.getAddr(), doctor.getCity(),
                    doctor.getPincode(),
                    doctor.getDepartment().getDepartmentName(),
                    doctor.getDoctorLanguages());
        } catch (Exception e) {
            System.out.println("Error Occurred while getting doctor details from email");
            e.printStackTrace();
            return null;
        }
    }

    // Fetch doctor from his phone number

    @Override
    public DoctorDetails getDoctorByPhoneNumber(String phoneNumber) {
        try {
            Doctor doctor = doctorRepository.findByPhoneNumber(phoneNumber);
            if (doctor != null) {
                OnlineDoctor onlineDoctor = onlineDoctorRepository.findByDoctor_DoctorId(doctor.getDoctorId());
                onlineDoctor.setOnline(true);
                // Setting the online doctor as true;
                onlineDoctorRepository.save(onlineDoctor);
                return new DoctorDetails(
                       doctor.getDoctorId(), doctor.getTitle(),
                       doctor.getFirstName(), doctor.getLastName(),
                       doctor.getEmail(), doctor.getPhoneNumber(),
                       doctor.getRegistration_number(), doctor.getDob(),
                       doctor.getGender(), doctor.getAddr(),
                       doctor.getCity(), doctor.getPincode(),
                       doctor.getDepartment().getDepartmentName(),
                       doctor.getDoctorLanguages()
                );
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error Occurred while verifying phone number");
            e.printStackTrace();
            return null;
        }
    }

    // ------------------------------------- Update Doctor details ----------------------------------------

    @Override
    public DoctorDetails updateDoctor(DoctorDetails doctorDetails, long doctorId) {
        Doctor doctor;

        Set<String> languagesSpeak = doctorDetails.getDoctorLanguages();
        Set<String> languages = new HashSet<>();

        for(String s: languagesSpeak)
            languages.add(s.toLowerCase());

        if(doctorRepository.findById(doctorId).isPresent()) {
            doctor = doctorRepository.findById(doctorId).get();
            doctor.setEmail(doctorDetails.getEmail());
            doctor.setAddr(doctorDetails.getAddr());
            doctor.setPincode(doctorDetails.getPincode());
            doctor.setCity(doctorDetails.getCity());
            doctor.setDoctorLanguages(languages);

            Doctor updatedDoctor = doctorRepository.save(doctor);

            return new DoctorDetails(
                    updatedDoctor.getDoctorId(),
                    updatedDoctor.getTitle(),
                    updatedDoctor.getFirstName(),
                    updatedDoctor.getLastName(),
                    updatedDoctor.getEmail(),
                    updatedDoctor.getPhoneNumber(),
                    updatedDoctor.getRegistration_number(),
                    updatedDoctor.getDob(),
                    updatedDoctor.getGender(),
                    updatedDoctor.getAddr(),
                    updatedDoctor.getCity(),
                    updatedDoctor.getPincode(),
                    updatedDoctor.getDepartment().getDepartmentName(),
                    updatedDoctor.getDoctorLanguages()
            );
        }
        else return null;

    }


    // ------------------------------ Logout doctor and set doctor online to false -----------------------------------

    @Override
    public boolean doctorLogout(long doctorId) {
        try {
            OnlineDoctor onlineDoctor = onlineDoctorRepository.findByDoctor_DoctorId(doctorId);
            onlineDoctor.setOnline(false);

            onlineDoctorRepository.save(onlineDoctor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


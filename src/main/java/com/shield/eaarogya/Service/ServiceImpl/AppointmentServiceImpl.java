package com.shield.eaarogya.Service.ServiceImpl;

import com.shield.eaarogya.DTO.AppointmentDetails;
import com.shield.eaarogya.Entity.*;
import com.shield.eaarogya.Repository.AppointmentRepository;
import com.shield.eaarogya.Repository.AppointmentStatusRepository;
import com.shield.eaarogya.Repository.DoctorRepository;
import com.shield.eaarogya.Service.AppointmentService;
import com.shield.eaarogya.Service.DepartmentService;
import com.shield.eaarogya.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentStatusRepository appointmentStatusRepository;

    @Transactional
    @Override
    public long requestAppointment(AppointmentDetails appointmentDetails) {
        try {
            Department department = departmentService.getDepartmentByName(appointmentDetails.getDepartmentName());
            Patient patient = patientService.getPatientByPatientId(appointmentDetails.getPatientId());

            Appointment appointment = new Appointment(
                    patient,
                    department,
                    appointmentDetails.getAppointmentTimestamp(),
                    appointmentDetails.getPreferredLanguage().toLowerCase()
            );
            Appointment savedAppointment = appointmentRepository.save(appointment);

            // Saving appointment status also
            AppointmentStatus appointmentStatus = new AppointmentStatus(patient, false);
            appointmentStatusRepository.save(appointmentStatus);

            return savedAppointment.getAppointmentId();

        } catch (DataIntegrityViolationException d) {
            return -1;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Occurred while requesting for an appointment.");
            return -1;
        }
    }

    @Override
    public List<AppointmentDetails> getAllAppointments() {
        try {
            List<AppointmentDetails> appointmentDetailsList = new ArrayList<>();

            List<Appointment> appointmentList = appointmentRepository.findAll();
            for (Appointment appointment : appointmentList) {
                appointmentDetailsList.add(new AppointmentDetails(
                        appointment.getAppointmentId(),
                        appointment.getAppointmentTimestamp(),
                        appointment.getPatient().getPatientId(),
                        appointment.getDepartment().getDepartmentName(),
                        appointment.getPreferredLanguage(),
                        appointment.isAccepted()
                ));
            }

            return appointmentDetailsList;
        } catch (Exception e) {
            System.out.println("Error Occured while fetching all appointments.");
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------- Appointment Accepted ----------------------------------------------------

    @Override
    public boolean appointmentAccepted(long appointmentId) {

        try {
            Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
            appointment.setAccepted(true);

            appointmentRepository.save(appointment);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------------- Check if appointment is accepted or not --------------------------------------------
    // We are checking this from AppointmentStatus Repository
    @Override
    public boolean isAppointmentAccepted(long patientId) {
        AppointmentStatus appointmentStatus = appointmentStatusRepository.findByPatient_PatientId(patientId);

        return appointmentStatus.isStatus();
    }

    // --------------------------- Delete Appointment Status using patientId -----------------------------------------
    @Override
    public boolean deleteAppointmentStatus(long patientId) {
        try {
            appointmentStatusRepository.delete(appointmentStatusRepository.findByPatient_PatientId(patientId));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------------------- Delete appointment using appointmentId ------------------------------------
    @Override
    public boolean deleteAppointment(long appointmentId) {

        try {
            Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
            Patient patient = appointment.getPatient();

            // Setting Appointment Status of that particular appointment to be True.
            AppointmentStatus appointmentStatus = appointmentStatusRepository.findByPatient_PatientId(patient.getPatientId());
            appointmentStatus.setStatus(true);
            appointmentStatusRepository.save(appointmentStatus);

            appointmentRepository.delete(appointment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // To revoke the appointment taken
    @Override
    public boolean deleteAppointmentByPatientId(long patientId) {
        try {
            Appointment appointment = appointmentRepository.findByPatient_PatientId(patientId);
            appointmentRepository.delete(appointment);

            // Also deleting the record from Appointment-Status table
            AppointmentStatus appointmentStatus = appointmentStatusRepository.findByPatient_PatientId(patientId);
            appointmentStatusRepository.delete(appointmentStatus);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Based on appointment Id, it will give the count of patient infront of him.
    @Override
    public int waitingPatients(long appointmentId) {
        try {
            Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
            List<Appointment> appointmentList = new ArrayList<>();
            if(appointment != null) {
                appointmentList = appointmentRepository
                        .findAllByDepartment_DepartmentNameAndAppointmentTimestampLessThan(
                        appointment.getDepartment().getDepartmentName(), appointment.getAppointmentTimestamp());
            }
            if(appointmentList != null)
                return appointmentList.size();
            else
                return 0;
        } catch (Exception e) {
            System.out.println("Error Occured while fetching number of waiting patients.");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<AppointmentDetails> getAppointmentsByDepartment(String departmentName) {
        try {
            List<Appointment> appointmentList = appointmentRepository.findAllByDepartment_DepartmentName(departmentName);

            List<AppointmentDetails> appointmentDetailsList = new ArrayList<>();

            for (Appointment appointment : appointmentList) {
                appointmentDetailsList.add(new AppointmentDetails(appointment.getAppointmentId(),
                        appointment.getAppointmentTimestamp(), appointment.getPatient().getPatientId(),
                        appointment.getDepartment().getDepartmentName(), appointment.getPreferredLanguage(),
                        appointment.isAccepted()));
            }

            return appointmentDetailsList;
        } catch (Exception e) {
            System.out.println("Error Occured fetching appointments based on department name.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkAppointment(long patientId) {
        try {
            Appointment appointment = appointmentRepository.findByPatient_PatientId(patientId);
            return appointment != null;
        } catch (Exception e) {
            System.out.println("Error while checking for appointments");
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<AppointmentDetails> getAppointmentByPreferredLanguageAndDepartmentName(long doctorId) {
        try {
            Doctor doctor;
            if(doctorRepository.findById(doctorId).isPresent()) {
                doctor = doctorRepository.findById(doctorId).get();
                Set<String> doctorLanguages = doctor.getDoctorLanguages();
                List<Appointment> appointmentList = appointmentRepository.findAllByDepartment_DepartmentName(doctor.getDepartment().getDepartmentName());

                List<AppointmentDetails> appointmentDetailsList = new ArrayList<>();

                for (Appointment appointment : appointmentList) {
                    String appointmentLanguage = appointment.getPreferredLanguage().toLowerCase();
                    // Check if the doctor speaks same language or not.
                    if(doctorLanguages.contains(appointmentLanguage))
                        appointmentDetailsList.add(new AppointmentDetails(appointment.getAppointmentId(),
                                appointment.getAppointmentTimestamp(), appointment.getPatient().getPatientId(),
                                appointment.getDepartment().getDepartmentName(), appointment.getPreferredLanguage(),
                                appointment.isAccepted()));
                }

                return appointmentDetailsList;
            }
            else return null;
        } catch (Exception e) {
            System.out.println("Error Occured fetching appointments based on department name.");
            e.printStackTrace();
            return null;
        }
    }
}

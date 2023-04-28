package com.shield.eaarogya.Service.ServiceImpl;

import com.shield.eaarogya.DTO.DateWiseConsultations;
import com.shield.eaarogya.Entity.Consultation;
import com.shield.eaarogya.Repository.ConsultationRepository;
import com.shield.eaarogya.Service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public long getAllConsultationsCount() {
        return (consultationRepository.findAll()).size();
    }

    // --------------------------------------------- Add Consultation ----------------------------------------------
    @Override
    public boolean addConsultation(Consultation consultation) {
        try {
            consultationRepository.save(consultation);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------------------------- Get Date-wise Consultations for Home page --------------------------------------------

    @Override
    public List<DateWiseConsultations> totalDateWiseConsultations() {
        List<Consultation> consultationList = consultationRepository.findAll();

        List<DateWiseConsultations> dateWiseConsultationsList = new ArrayList<>();

        Map<Date, Long> totalConsultations = new TreeMap<>();

        for(Consultation consultation: consultationList) {
            totalConsultations.merge(consultation.getConsultationDate(), 1L, Long::sum);
        }

        for (Map.Entry<Date, Long> entry : totalConsultations.entrySet()) {
            Date key = entry.getKey();
            Long value = entry.getValue();
            dateWiseConsultationsList.add(
                    new DateWiseConsultations(key, value)
            );
        }

        return dateWiseConsultationsList;
    }

    // ----------------------- Date-wise consultations for doctor dashboard using doctorId -----------------------------
    @Override
    public List<DateWiseConsultations> totalDateWiseConsultations(long doctorId) {
        List<Consultation> consultationList = consultationRepository.findAllByDoctor_DoctorId(doctorId);

        List<DateWiseConsultations> dateWiseConsultationsList = new ArrayList<>();

        Map<Date, Long> totalConsultations = new TreeMap<>();

        for(Consultation consultation: consultationList) {
            totalConsultations.merge(consultation.getConsultationDate(), 1L, Long::sum);
        }

        for (Map.Entry<Date, Long> entry : totalConsultations.entrySet()) {
            Date key = entry.getKey();
            Long value = entry.getValue();
            dateWiseConsultationsList.add(
                    new DateWiseConsultations(key, value)
            );
        }

        return dateWiseConsultationsList;
    }

    // ------------------------------- Total Consultations done by patient ---------------------------------------------

    @Override
    public long totalConsultationByPatient(long patientId) {
        return consultationRepository.findAllByPatient_PatientId(patientId).size();
    }


    // ------------------------------- Total Consultations done by doctor ---------------------------------------------

    @Override
    public long totalConsultationByDoctor(long doctorId) {
        return consultationRepository.findAllByDoctor_DoctorId(doctorId).size();
    }

    // ---------------------------- Total Daily Consultation Done By doctor --------------------------------------------

    @Override
    public long totalDailyConsultationByDoctor(long doctorId) {

        List<Consultation> consultationList = consultationRepository.findAllByDoctor_DoctorId(doctorId);

        // We'll compare the dates in string format, we'll convert consultation date and current date to the below pattern
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        long count = 0;
        for(Consultation consultation: consultationList) {
            if(currentDate.equals(dateFormat.format(consultation.getConsultationDate())))
                count++;
        }

        return count;
    }
}

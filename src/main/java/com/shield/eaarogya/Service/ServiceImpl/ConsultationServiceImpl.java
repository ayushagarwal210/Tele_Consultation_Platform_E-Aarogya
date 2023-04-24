package com.shield.eaarogya.Service.ServiceImpl;

import com.shield.eaarogya.Entity.Consultation;
import com.shield.eaarogya.Repository.ConsultationRepository;
import com.shield.eaarogya.Service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public long getAllConsultationsCount() {
        return (consultationRepository.findAll()).stream().count();
    }

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
}

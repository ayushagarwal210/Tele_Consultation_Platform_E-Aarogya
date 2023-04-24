package com.shield.eaarogya.Service;

import com.shield.eaarogya.Entity.Consultation;

public interface ConsultationService {
    long getAllConsultationsCount();

    boolean addConsultation(Consultation consultation);
}

package com.shield.eaarogya.Repository;

import com.shield.eaarogya.Entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {

    List<Consultation> findAllByDoctor_DoctorId(long doctorId);

    List<Consultation> findAllByPatient_PatientId(long patientId);
}

package com.shield.eaarogya.Repository;

import com.shield.eaarogya.Entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentStatusRepository extends JpaRepository<AppointmentStatus, Integer> {

    AppointmentStatus findByPatient_PatientId(long patientId);
}

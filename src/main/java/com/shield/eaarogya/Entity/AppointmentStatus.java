package com.shield.eaarogya.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// This class is created to check the status of appointment, and to enable the join button on the patient side whenever
// doctor accepts the appointment, also this entry will be unique for a patient, so it has to be deleted accordingly.

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppointmentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient", referencedColumnName = "patientId", unique = true)
    private Patient patient;

    private boolean status;

    // --------------------------------------- Constructor -------------------------------------------

    public AppointmentStatus(Patient patient, boolean status) {
        this.patient = patient;
        this.status = status;
    }
}

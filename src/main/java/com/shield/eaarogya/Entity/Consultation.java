package com.shield.eaarogya.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consultationId;

    @Column(name = "consultation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date consultationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor", referencedColumnName = "doctorId")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient", referencedColumnName = "patientId")
    private Patient patient;

    // --------------------------------------------- Constructor ----------------------------------------------------

    public Consultation(Date consultationDate, Doctor doctor, Patient patient) {
        this.consultationDate = consultationDate;
        this.doctor = doctor;
        this.patient = patient;
    }
}

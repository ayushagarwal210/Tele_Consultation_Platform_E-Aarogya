package com.shield.eaarogya.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {

    // Our Asumptions:
    // 1. One patient can have only one appointment at a particular time
    // 2. If he is having an appointment then he cannot have another appointment at that time

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appointmentId;
    // Note: Basic attribute type cannot be persistence entity


    @Column(name="isAccepted", nullable = false)
    private boolean isAccepted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient", referencedColumnName = "patientId", unique = true)
    private Patient patient;

    @Column(name = "appointment_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department", referencedColumnName = "departmentId")
    private Department department;

    @Column(name = "preferred_language", nullable = false)
    private String preferredLanguage;

    //----------------------------------------- Constructor ------------------------------------------------

    public Appointment(Patient patient, Department department, Date appointmentTimestamp, String preferredLanguage) {
        this.patient = patient;
        this.department = department;
        this.appointmentTimestamp = appointmentTimestamp;
        this.preferredLanguage = preferredLanguage;
        this.isAccepted = false;
    }

    // ------------------------------------------- ToString() ----------------------------------------------


    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", patient=" + patient +
                ", appointmentTimestamp=" + appointmentTimestamp +
                ", department=" + department +
                ", preferredLanguage='" + preferredLanguage + '\'' +
                ", isAccepted=" + isAccepted +
                '}';
    }
}

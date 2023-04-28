package com.shield.eaarogya.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetails {

    private long appointmentId;
//    private String patientName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentTimestamp;
    private long patientId;
    private String departmentName;

    private String preferredLanguage;

    private boolean isAccepted;

    // ---------------------------------------- Constructor --------------------------------------------

    public AppointmentDetails(Date appointmentTimestamp, long patientId, String departmentName, String preferredLanguage) {
        this.appointmentTimestamp = appointmentTimestamp;
        this.patientId = patientId;
        this.departmentName = departmentName;
        this.preferredLanguage = preferredLanguage;
    }

    // ------------------------------- toString ----------------------------------------------


    @Override
    public String toString() {
        return "AppointmentDetails{" +
                "appointmentId=" + appointmentId +
                ", appointmentTimestamp=" + appointmentTimestamp +
                ", patientId=" + patientId +
                ", departmentName='" + departmentName + '\'' +
                ", preferredLanguage='" + preferredLanguage + '\'' +
                ", isAccepted=" + isAccepted +
                '}';
    }
}

package com.shield.eaarogya.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateWiseConsultations {

    @Temporal(TemporalType.DATE)
    private Date dateOfConsultation;

    private long totalConsultations;
}

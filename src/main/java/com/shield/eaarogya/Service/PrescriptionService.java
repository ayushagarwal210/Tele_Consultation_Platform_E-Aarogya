package com.shield.eaarogya.Service;

import com.shield.eaarogya.DTO.DailyLogDetails;
import com.shield.eaarogya.DTO.FollowUpDetails;
import com.shield.eaarogya.DTO.PrescriptionDetails;

import java.util.List;

public interface PrescriptionService {

//    public List<Prescription> getPrescriptions(Long patientId);
    public List<PrescriptionDetails> getPrescriptionsPatient(long patientId);

    public List<PrescriptionDetails> getPrescriptionsDoctor(long patientId);

    public List<PrescriptionDetails> getAllPrescriptions();

    public PrescriptionDetails addPrescription(PrescriptionDetails prescriptionDetails);

    public PrescriptionDetails getPrescriptionById(int prescriptionId);

    public List<FollowUpDetails> getFollowUpDetails(long patientId);

    public List<DailyLogDetails> doctorDailyLog(long doctorId);
}

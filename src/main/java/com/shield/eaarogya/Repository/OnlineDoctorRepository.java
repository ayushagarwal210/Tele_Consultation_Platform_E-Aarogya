package com.shield.eaarogya.Repository;

import com.shield.eaarogya.Entity.OnlineDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineDoctorRepository extends JpaRepository<OnlineDoctor, Long> {

    OnlineDoctor findByDoctor_DoctorId(long doctorId);

}

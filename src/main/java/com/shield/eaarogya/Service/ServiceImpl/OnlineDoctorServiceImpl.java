package com.shield.eaarogya.Service.ServiceImpl;

import com.shield.eaarogya.Entity.OnlineDoctor;
import com.shield.eaarogya.Repository.OnlineDoctorRepository;
import com.shield.eaarogya.Service.OnlineDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnlineDoctorServiceImpl implements OnlineDoctorService {

    @Autowired
    OnlineDoctorRepository onlineDoctorRepository;

    @Override
    public int totalDoctorsOnline() {
        List<OnlineDoctor> onlineDoctorList = onlineDoctorRepository.findAll();
        int count = 0;
        for(OnlineDoctor onlineDoctor: onlineDoctorList) {
            if(onlineDoctor.isOnline())
                count++;
        }
        return count;
    }
}

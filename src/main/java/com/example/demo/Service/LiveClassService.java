package com.example.demo.Service;

import com.example.demo.Entity.Enrollment;
import com.example.demo.Entity.LiveClass;
import com.example.demo.Entity.User;
import com.example.demo.Repository.EnrollmentRepository;
import com.example.demo.Repository.LiveClassRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveClassService {

    @Autowired
    private LiveClassRepository liveClassRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    //create Live class

    public LiveClass createLiveClass(LiveClass liveClass){

        if(liveClass.getEndTime().isBefore(liveClass.getStartTime())){
            throw new RuntimeException("end time must be after start time");
        }

        return liveClassRepository.save(liveClass);
    }

    public void assignUser(Long userId,Long classId){

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found by this id" +userId));
        LiveClass liveClass = liveClassRepository.findById(classId).orElseThrow(() -> new RuntimeException("class  not found by this id" +classId));


        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);

        for(Enrollment enrollment : enrollments){
            LiveClass exisisting = enrollment.getLiveClass();

            if(isOverlapping(exisisting,liveClass)){
                throw  new RuntimeException("Schedule conflict detected");
            }
        }

        enrollmentRepository.save(new Enrollment(null,user,liveClass));
    }


    public boolean isOverlapping(LiveClass a, LiveClass b){

        return a.getStartTime().isBefore(b.getEndTime()) && b.getStartTime().isBefore(a.getEndTime());
    }

    public List<Enrollment> getUsersInClass(Long id){

        return enrollmentRepository.findByUserId(id);
    }
}

package com.example.demo.Service;

import com.example.demo.Entity.Enrollment;
import com.example.demo.Entity.LiveClass;
import com.example.demo.Entity.User;
import com.example.demo.Repository.EnrollmentRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    public Map<String, List<LiveClass>> getUserScheudle(Long userId){

        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);

        LocalDateTime now = LocalDateTime.now();

        Map<String , List<LiveClass>> schedule = new HashMap<>();

        schedule.put("upcoming",new ArrayList<>());
        schedule.put("in progress",new ArrayList());
        schedule.put("historical",new ArrayList());

        for(Enrollment e : enrollments){

            LiveClass c = e.getLiveClass();

            if(now.isBefore(e.getLiveClass().getStartTime())){
                schedule.get("upcoming").add(c);
            }
            else if (now.isAfter(e.getLiveClass().getEndTime())){
                schedule.get("historical").add(c);
            }
            else{
                schedule.get("in progress").add(c);
            }
        }

        return schedule;
    }

    public User createUser ( User user){

        return userRepository.save(user);
    }

    public List<User> getUsers(){

        return userRepository.findAll();
    }

    public User updateUser(Long id ,User user){
        User newUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        newUser.setName(user.getName());

        return userRepository.save(newUser);

    }
}

package com.example.demo.Controller;

import com.example.demo.Entity.Enrollment;
import com.example.demo.Entity.LiveClass;
import com.example.demo.Service.LiveClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/live-classes")
public class LiveClassController {

    private LiveClassService liveClassService;

    @PostMapping("/createClass")
    public ResponseEntity<LiveClass> createLiveClass(@RequestBody LiveClass liveclass){

        LiveClass newLiveClass = liveClassService.createLiveClass(liveclass);

        return new ResponseEntity<>(newLiveClass, HttpStatus.CREATED);
    }

   @PostMapping("/assign/{classId}/{userId}")
    public ResponseEntity<String> assign(@PathVariable  Long classId, @PathVariable Long userId){

          liveClassService.assignUser(classId,userId);
          return new ResponseEntity<>("Assigned",HttpStatus.OK);
   }

   @GetMapping("/users/{classId}")
    public ResponseEntity<List<?>> users(@PathVariable Long classId){

       List<Enrollment> usersInClass = liveClassService.getUsersInClass(classId);

       return new ResponseEntity<>(usersInClass,HttpStatus.OK);
   }
}

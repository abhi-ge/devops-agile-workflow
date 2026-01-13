package com.example.demo.Controller;

import com.example.demo.Entity.LiveClass;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){

        User newUser = userService.createUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/schedule/{userId}")
    public ResponseEntity<?> schedule(@PathVariable Long userId){

        Map<String, List<LiveClass>> userScheudle = userService.getUserScheudle(userId);

        return new ResponseEntity<>(userScheudle,HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUser(){

        List<User> users = userService.getUsers();

        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PutMapping("updateUser/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User user){

        User updatedUser = userService.updateUser(id,user);

        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }
}

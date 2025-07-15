package com.jag.ExpenseTracker.controller;

import com.jag.ExpenseTracker.dtos.MessageDTO;
import com.jag.ExpenseTracker.models.User;
import com.jag.ExpenseTracker.service.users.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/register")
@RestController
public class RegisterController {
    @Autowired
    UserServiceImp userServiceImp;

    @PostMapping("/user")
    public ResponseEntity<MessageDTO> saveUser(@RequestBody User user) {
        return userServiceImp.saveUser(user,"USER");
    }

    @PostMapping("/admin")
    public ResponseEntity<MessageDTO> saveAdmin(@RequestBody User user) {
        return userServiceImp.saveUser(user,"ADMIN");
    }
}

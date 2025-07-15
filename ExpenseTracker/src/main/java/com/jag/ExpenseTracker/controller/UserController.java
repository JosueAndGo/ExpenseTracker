package com.jag.ExpenseTracker.controller;

import com.jag.ExpenseTracker.dtos.EmailDto;
import com.jag.ExpenseTracker.dtos.LoginUserDTO;
import com.jag.ExpenseTracker.dtos.MessageDTO;
import com.jag.ExpenseTracker.models.User;
import com.jag.ExpenseTracker.service.users.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;

    @PostMapping("/findRol")
    public ResponseEntity<User> getUserByEmail(@RequestBody EmailDto request) {
        Optional<User> userOpt = userServiceImp.getUserbyEmail(request.getEmail());

        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/login")
    public ResponseEntity<MessageDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        return userServiceImp.loginUser(loginUserDTO);
    }
}

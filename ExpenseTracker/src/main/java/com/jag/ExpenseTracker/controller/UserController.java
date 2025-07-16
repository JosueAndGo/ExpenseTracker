package com.jag.ExpenseTracker.controller;

import com.jag.ExpenseTracker.dtos.user.EmailDto;
import com.jag.ExpenseTracker.dtos.user.LoginUserDTO;
import com.jag.ExpenseTracker.commons.MessageCustom;
import com.jag.ExpenseTracker.models.User;
import com.jag.ExpenseTracker.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/findRol")
    public ResponseEntity<User> getUserByEmail(@RequestBody EmailDto request) {
        Optional<User> userOpt = userService.getUserbyEmail(request.getEmail());

        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/login")
    public ResponseEntity<MessageCustom> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        return userService.loginUser(loginUserDTO);
    }
}

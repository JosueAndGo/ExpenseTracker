package com.jag.ExpenseTracker.controller;

import com.jag.ExpenseTracker.commons.MessageCustom;
import com.jag.ExpenseTracker.dtos.user.UserRegisterDTO;
import com.jag.ExpenseTracker.models.User;
import com.jag.ExpenseTracker.service.users.UserService;
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
    UserService userService;

    @PostMapping("/user")
    public ResponseEntity<MessageCustom> saveUser(@RequestBody UserRegisterDTO user) {
        return userService.saveUser(user,"USER");
    }

    @PostMapping("/admin")
    public ResponseEntity<MessageCustom> saveAdmin(@RequestBody UserRegisterDTO user) {
        return userService.saveUser(user,"ADMIN");
    }
}

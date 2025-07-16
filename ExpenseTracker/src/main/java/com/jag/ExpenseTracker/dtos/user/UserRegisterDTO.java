package com.jag.ExpenseTracker.dtos.user;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String email;
    private String password;
    private String username;
}

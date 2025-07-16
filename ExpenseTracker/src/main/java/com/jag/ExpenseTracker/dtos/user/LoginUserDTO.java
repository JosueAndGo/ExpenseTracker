package com.jag.ExpenseTracker.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO {
    private String email;
    private String userName;
    private String password;
}

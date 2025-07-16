package com.jag.ExpenseTracker.service.jwt;


import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
     String generateToken(UserDetails userDetails);
     String extractUserName(String token);
    boolean validateToken(String token, UserDetails userDetails);

}

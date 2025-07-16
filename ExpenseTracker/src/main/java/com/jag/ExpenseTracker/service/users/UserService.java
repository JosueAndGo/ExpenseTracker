package com.jag.ExpenseTracker.service.users;

import com.jag.ExpenseTracker.dtos.user.LoginUserDTO;
import com.jag.ExpenseTracker.commons.MessageCustom;
import com.jag.ExpenseTracker.dtos.user.UserRegisterDTO;
import com.jag.ExpenseTracker.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserbyEmail(String email);
    User getByUsername( String userName);
    ResponseEntity<MessageCustom> saveUser(UserRegisterDTO user, String roleName);
    ResponseEntity<MessageCustom> loginUser(LoginUserDTO loginUserDTO);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

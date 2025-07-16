package com.jag.ExpenseTracker.service.users;

import com.jag.ExpenseTracker.dtos.user.LoginUserDTO;
import com.jag.ExpenseTracker.commons.MessageCustom;
import com.jag.ExpenseTracker.dtos.user.UserRegisterDTO;
import com.jag.ExpenseTracker.models.Roles;
import com.jag.ExpenseTracker.models.User;
import com.jag.ExpenseTracker.models.UserPrincipal;
import com.jag.ExpenseTracker.persistance.RoleRepository;
import com.jag.ExpenseTracker.persistance.UserRepository;
import com.jag.ExpenseTracker.service.jwt.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public  class UserServiceImp implements UserDetailsService, UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JWTService jwtService;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public Optional<User> getUserbyEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getByUsername( String userName) {
        return userRepository.findByUsername(userName);
    }




    public ResponseEntity<MessageCustom> saveUser(UserRegisterDTO user, String roleName) {
        MessageCustom messageCustom = new MessageCustom();
        boolean emailExist = getUserbyEmail(user.getEmail()).isPresent();
        boolean userExist = getByUsername(user.getUsername()) != null;
        log.info("SaveUser service:{}", user);

        if (emailExist){
            messageCustom.setMenssage("The email is already in use, try with another");
            messageCustom.setStatusCode(400);
            log.info("User created Exepcion:{}", messageCustom.getMenssage() );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageCustom);

        }
        if (userExist){
            messageCustom.setMenssage("The Username is already in use, try with another");
            messageCustom.setStatusCode(400);
            log.info("User created Exepcion:{}", messageCustom.getMenssage() );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageCustom);
        }


        Roles userRole = roleRepository.findByRole(roleName);


        User user1 = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .createAt(LocalDateTime.now())
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user1);
        messageCustom.setStatusCode(201);
        messageCustom.setMenssage("user Created");
        log.info("User created:{}, with email, {}", messageCustom, user.getEmail() );
        return ResponseEntity.status(HttpStatus.CREATED).body(messageCustom);

    }


    public ResponseEntity<MessageCustom> loginUser(LoginUserDTO loginUserDTO) {
        MessageCustom messageCustom = new MessageCustom();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDTO.getUserName(), loginUserDTO.getPassword())
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                String token = jwtService.generateToken(userDetails);

                messageCustom.setMenssage(token);
                messageCustom.setStatusCode(200);
                return ResponseEntity.status(HttpStatus.OK).body(messageCustom);
            }
        } catch (Exception e) {
            log.error("Authentication failed", e);
        }

        messageCustom.setMenssage("Fail");
        messageCustom.setStatusCode(400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageCustom);
    }




        @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByUsername(username);
        if(user ==  null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user) ;
    }
}

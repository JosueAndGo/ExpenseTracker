package com.jag.ExpenseTracker.service.users;

import com.jag.ExpenseTracker.dtos.LoginUserDTO;
import com.jag.ExpenseTracker.dtos.MessageDTO;
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
public  class UserServiceImp implements UserDetailsService {
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




    public ResponseEntity<MessageDTO> saveUser(User user,String roleName) {
        MessageDTO messageDTO = new MessageDTO();
        boolean emailExist = getUserbyEmail(user.getEmail()).isPresent();
        boolean userExist = getByUsername(user.getUsername()) != null;
        log.info("SaveUser service:{}", user);

        if (emailExist){
            messageDTO.setMenssage("The email is already in use, try with another");
            messageDTO.setStatusCode(400);
            log.info("User created Exepcion:{}",messageDTO.getMenssage() );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDTO);

        }
        if (userExist){
            messageDTO.setMenssage("The Username is already in use, try with another");
            messageDTO.setStatusCode(400);
            log.info("User created Exepcion:{}",messageDTO.getMenssage() );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDTO);
        }


        Roles userRole = roleRepository.findByRole(roleName);

        user.setRoles(Set.of(userRole));
        user.setCreateAt(LocalDateTime.now());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        messageDTO.setStatusCode(201);
        messageDTO.setMenssage("user Created");
        log.info("User created:{}, with email, {}",messageDTO, user.getEmail() );
        return ResponseEntity.status(HttpStatus.CREATED).body(messageDTO);

    }


    public ResponseEntity<MessageDTO> loginUser(LoginUserDTO loginUserDTO) {
        MessageDTO messageDTO = new MessageDTO();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDTO.getUserName(), loginUserDTO.getPassword())
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                String token = jwtService.generateToken(userDetails);

                messageDTO.setMenssage(token);
                messageDTO.setStatusCode(200);
                return ResponseEntity.status(HttpStatus.OK).body(messageDTO);
            }
        } catch (Exception e) {
            log.error("Authentication failed", e);
        }

        messageDTO.setMenssage("Fail");
        messageDTO.setStatusCode(400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDTO);
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

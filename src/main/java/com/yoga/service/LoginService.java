package com.yoga.service;

import com.yoga.component.Role;
import com.yoga.datamodel.User;
import com.yoga.dto.request.LoginRequest;
import com.yoga.dto.response.LoginResponse;
import com.yoga.exception.InvalidCredentialException;
import com.yoga.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class LoginService {

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private JWTService jwtService;

    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    public LoginService(UserRepository userRepository,AuthenticationManager authenticationManager,
                        JWTService jwtService,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }



    public LoginResponse userLogin(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            if (authentication.isAuthenticated()) {

               // logger.info("user is authenticated");

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                String token = jwtService.generateToken(userDetails);

                //logger.info("the token is : "+token);

                User getUser = userRepository.getByEmail(userDetails.getUsername());

                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setUserId(getUser.getUserId());
                loginResponse.setEmail(getUser.getEmail());
                loginResponse.setRole(getUser.getRole());
                loginResponse.setToken(token);

                if (getUser.getRole().equals(Role.EMPLOYEE)){
                    loginResponse.setBranchId(getUser.getEmployee().getBranch().getBranchId());
                }
                return loginResponse;
            }
            throw new InvalidCredentialException("User credential is invalid");
        } catch (RuntimeException e) {
            throw new InvalidCredentialException("User credential is invalid");
        }



    }
}

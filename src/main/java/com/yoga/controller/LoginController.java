package com.yoga.controller;


import com.yoga.dto.request.LoginRequest;
import com.yoga.dto.response.LoginResponse;
import com.yoga.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class LoginController {

       private LoginService loginService;

       @Autowired
       public LoginController(LoginService loginService){
           this.loginService = loginService;
       }

       @PostMapping("/login")
       public LoginResponse userLogin(@RequestBody LoginRequest loginRequest){
           return loginService.userLogin(loginRequest);
       }

       @GetMapping("/test")
       public String test(){
           return "Apllication run correctly";
       }
}

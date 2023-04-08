package com.tahafurkan.sandbox.Walk.With.Me.App.controller;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.LoginDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.RegisterDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping(value = {"/login" , "/signin"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register" , "signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }
}

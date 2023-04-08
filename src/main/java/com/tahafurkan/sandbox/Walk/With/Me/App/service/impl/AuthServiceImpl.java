package com.tahafurkan.sandbox.Walk.With.Me.App.service.impl;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.Role;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.User;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.LoginDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.RegisterDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.exception.APIException;
import com.tahafurkan.sandbox.Walk.With.Me.App.repository.RoleRepository;
import com.tahafurkan.sandbox.Walk.With.Me.App.repository.UserRepository;
import com.tahafurkan.sandbox.Walk.With.Me.App.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail() , loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User Logged-in successfully";
    }

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST , "Username is already exists.");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new APIException(HttpStatus.BAD_REQUEST , "Email is already exists.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully.";
    }
}

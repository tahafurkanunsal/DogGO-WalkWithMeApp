package com.tahafurkan.sandbox.Walk.With.Me.App.service;

import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.LoginDto;
import com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}

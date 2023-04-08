package com.tahafurkan.sandbox.Walk.With.Me.App.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String usernameOrEmail;
    private String password;
}

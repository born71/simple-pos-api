package com.born.simplepos.springSimplePos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;  // changed from name
    private String email;
    private String password;
}

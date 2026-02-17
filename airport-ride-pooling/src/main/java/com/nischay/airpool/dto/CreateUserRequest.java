package com.nischay.airpool.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    public String email;
    public String password;
}

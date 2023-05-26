package com.accountms.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class SignInRequest {
    private String email;
    private String password;
}

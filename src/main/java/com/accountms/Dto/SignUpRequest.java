package com.accountms.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@Setter
@Getter
@ToString
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String address;
    private String phone;
    private Date birthDate;
    private List<String> roles;
}

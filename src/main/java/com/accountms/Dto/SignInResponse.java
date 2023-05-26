package com.accountms.Dto;

import com.accountms.entity.User;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
@Data
public class SignInResponse {
    User user;
    String token;

    public SignInResponse(UserDetails loadUserByUsername, String signIn) {
        this.user= (User) loadUserByUsername;
        this.token=signIn;
    }
}

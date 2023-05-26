package com.accountms.controller;

import com.accountms.Dto.SignInRequest;
import com.accountms.Dto.SignInResponse;
import com.accountms.Dto.SignUpRequest;
import com.accountms.service.AuthService;
import com.accountms.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    public final AuthService authService;
    public final UserServiceImpl userService;
    @PostMapping("signUp")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUp) throws Exception {
        System.out.println(signUp);
        return ResponseEntity.ok().body(authService.register(signUp));
    }
    @PostMapping("signIn")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest){
        System.out.println("ovvjhsuieogoipegiop");
        var signInRespone=new SignInResponse(userService.loadUserByUsername(signInRequest.getEmail())
                ,authService.signIn(signInRequest));
        return ResponseEntity.ok().body(signInRespone);
    }
}

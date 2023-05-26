package com.accountms.service;

import com.accountms.Dto.SignInRequest;
import com.accountms.Dto.SignUpRequest;
import com.accountms.config.JwtService;
import com.accountms.entity.Role;
import com.accountms.entity.User;
import com.accountms.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    public final UserServiceImpl userService;
    public final RoleRepository roleRepository;
    public final JwtService jwtService;
    public final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncode;

    public String register(SignUpRequest signUpRequest) throws Exception {
        Set<Role> roles = new HashSet<>();
        for (String r : signUpRequest.getRoles()) {
            roles.add(roleRepository.findByNameContainingIgnoreCase(r).orElseThrow());
        }
        var user = User.builder().email(signUpRequest.getEmail())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .password(passwordEncode.encode(signUpRequest.getPassword()))
                .address(signUpRequest.getAddress())
                .birthDate(signUpRequest.getBirthDate())
                .phone(signUpRequest.getPhone())
                .gender(signUpRequest.getGender())
                .roles(roles)
                .build();
        userService.createUser(user);
        return jwtService.generateToken(user);
    }

    public String signIn(SignInRequest signInRequest) {
        var user = userService.loadUserByUsername(signInRequest.getEmail());
        System.out.println(user);
        System.out.println(signInRequest.getEmail() + " /////" + signInRequest.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInRequest.getEmail()
                , signInRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);
        System.out.println(authenticationToken.isAuthenticated());
        return jwtService.generateToken((User) user);
    }
}

package com.accountms.config;

import com.accountms.service.UserServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtAutFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request
            , @Nonnull HttpServletResponse response
            , @Nonnull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        final String jwt;
        final String email;
        if ((header == null || !header.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = header.substring(7);
        email = jwtService.extractEmail(jwt);
        Claims claims= jwtService.getAllClaims(jwt);
        System.out.println(email);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails user = this.userService.loadUserByUsername(email);
            if (jwtService.isTokenValid(jwt, user)) {
                List<String> authorities = (List<String>) claims.get("authorities");
                Collection<? extends GrantedAuthority> grantedAuthorities =authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
                        null,
                        grantedAuthorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}

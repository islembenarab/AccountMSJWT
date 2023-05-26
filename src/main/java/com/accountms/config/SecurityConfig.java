package com.accountms.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {

    private final JwtAutFilter jwtAutFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity.cors().disable()
//               .cors().configurationSource(new CorsConfigurationSource() {
//                   @Override
//                   public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                       CorsConfiguration config = new CorsConfiguration();
//                       config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
//                       config.setAllowedMethods(Collections.singletonList("*"));
//                       config.setAllowCredentials(true);
//                       config.setAllowedHeaders(Collections.singletonList("*"));
//                       config.setExposedHeaders(Arrays.asList("Authorization"));
//                       config.setMaxAge(3600L);
//                       return config;
//                   }
//               })

               .csrf()
               .disable()
               .authorizeHttpRequests()
               .requestMatchers("/api/auth/**").permitAll()
               .anyRequest().hasAuthority("GATEWAY")
               .and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authenticationProvider(authProvider)
               .addFilterBefore(jwtAutFilter, UsernamePasswordAuthenticationFilter.class);

       return httpSecurity.build();
   }



}

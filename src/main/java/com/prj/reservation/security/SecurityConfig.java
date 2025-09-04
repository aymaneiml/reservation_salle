package com.prj.reservation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prj.reservation.security.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(c->c.disable())
            .csrf(c->c.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/rooms/**").hasAnyRole("ADMIN","CLIENT")
                .requestMatchers(HttpMethod.POST,"/rooms").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/rooms/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/rooms/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/hotels/**").hasAnyRole("ADMIN","CLIENT")
                .requestMatchers(HttpMethod.POST,"/hotels/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/hotels/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/hotels/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/reservations").hasAnyRole("CLIENT")
                .requestMatchers(HttpMethod.GET,"/reservations/me").hasAnyRole("CLIENT")
                .requestMatchers(HttpMethod.GET,"/reservations").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/reservations/{id}/confirm").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/reservations/{id}/cancel").hasAnyRole("ADMIN", "CLIENT")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .authenticationManager(authenticationManager(http));
        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        var authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }

}
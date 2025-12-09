package com.mystiquex.employee_leave_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.withUsername("Admin").password("Admin").roles("ADMIN").build();
        UserDetails hr = User.withUsername("Hr").password("Hr").roles("HR").build();
        UserDetails viewer = User.withUsername("Viewer").password("Viewer").roles("VIEWER").build();

        return new InMemoryUserDetailsManager(admin, hr, viewer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(httpConfigurer -> httpConfigurer.disable()).authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/employee/**", "/api/leave/**")
                        .hasAnyRole("ADMIN", "HR", "VIEWER")
                        .requestMatchers("/api/**")
                        .hasAnyRole("ADMIN", "HR").anyRequest().authenticated()).httpBasic(httpBasic -> httpBasic
                .realmName("Employee Leave Management System"));

        return httpSecurity.build();

    }
}

package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.User.JwtResponse;
import com.materio.materio_backend.dto.User.LoginRequest;
import com.materio.materio_backend.dto.User.SignupRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    @PostConstruct
    void initRoles();

    JwtResponse authenticateUser(LoginRequest loginRequest);

    @Transactional
    void registerUser(SignupRequest signUpRequest) throws Exception;
}

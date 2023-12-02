package com.tech.bb.moviesservice.service;

import com.tech.bb.moviesservice.entity.Users;

import java.util.Optional;

public interface AuthenticationService {
    Optional<Users> authenticate(String apiKey);
}

package com.tech.bb.moviesservice.service;

import com.tech.bb.moviesservice.entity.Users;
import com.tech.bb.moviesservice.exception.GenericException;
import com.tech.bb.moviesservice.repository.UsersRepository;
import com.tech.bb.moviesservice.utils.MovieServiceConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UsersRepository userRepository;
    public AuthenticationServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Users> authenticate(String apiKey) {
        if(Strings.isNotEmpty(apiKey)){
            return userRepository.findByApiKey(apiKey);
        }else {
            throw new GenericException(MovieServiceConstants.EXCEPTION_MISSING_API_KEY);
        }
    }
}

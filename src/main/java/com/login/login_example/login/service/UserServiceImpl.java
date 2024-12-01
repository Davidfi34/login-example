package com.login.login_example.login.service;

import com.login.login_example.infra.errors.IntegrityValidation;
import com.login.login_example.login.domain.Dto.RegistrationUserData;
import com.login.login_example.login.domain.User;
import com.login.login_example.login.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UsersRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void registerUser(RegistrationUserData registrationUserData) {

        UserDetails user = userRepository.findByUsername(registrationUserData.username());
        if (user != null) {
            throw new IntegrityValidation("The user name is already in use");
        }
        String encryptedPassword = passwordEncoder.encode(registrationUserData.password());

        User newUser = new User();
        newUser.setUsername(registrationUserData.username());
        newUser.setPassword(encryptedPassword);

        userRepository.save(newUser);

    }
}

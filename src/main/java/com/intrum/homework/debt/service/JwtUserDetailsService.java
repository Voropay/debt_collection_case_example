package com.intrum.homework.debt.service;

import com.google.common.base.Strings;
import com.intrum.homework.debt.domain.user.User;
import com.intrum.homework.debt.domain.user.UserEntity;
import com.intrum.homework.debt.domain.user.UserRequest;
import com.intrum.homework.debt.repository.UserRepository;
import com.intrum.homework.debt.domain.user.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("No userEntity found with username '%s'.", username));
        } else {
            return new JwtUser(
                    userEntity.getId(),
                    userEntity.getUsername(),
                    userEntity.getEmail(),
                    userEntity.getPassword()
            );
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity registerNewUserAccount(UserRequest userRO) throws UserRegistrationException{
        if(Strings.isNullOrEmpty(userRO.getEmail())) {
            throw new UserRegistrationException("email is empty");
        }
        if(Strings.isNullOrEmpty(userRO.getUsername())) {
            throw new UserRegistrationException("username is empty");
        }
        if(Strings.isNullOrEmpty(userRO.getPassword())) {
            throw new UserRegistrationException("password is empty");
        }
        if(userRepository.findByUsername(userRO.getUsername()) != null) {
            throw new UserRegistrationException("username already exist");
        }

        final UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRO.getUsername());
        userEntity.setEmail(userRO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRO.getPassword()));

        return userRepository.save(userEntity);
    }
}

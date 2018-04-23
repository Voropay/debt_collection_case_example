package com.intrum.homework.debt.service;

import com.intrum.homework.debt.domain.user.User;
import com.intrum.homework.debt.domain.user.UserEntity;
import com.intrum.homework.debt.domain.user.UserRequest;

public interface UserService {
    UserEntity registerNewUserAccount(UserRequest user);
    User findByUsername(String username);
}

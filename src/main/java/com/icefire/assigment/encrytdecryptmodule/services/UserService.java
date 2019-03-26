package com.icefire.assigment.encrytdecryptmodule.services;

import com.icefire.assigment.encrytdecryptmodule.models.User;

import javax.crypto.SecretKey;
import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findById(long id);
    User findByName(String name);
    void saveUser(User user);
    User updateUser(User user);
    void deleteUserById(long id);
    void deleteAllUsers();
    boolean isUserExist(User user);
    SecretKey generateSecretKey();
}

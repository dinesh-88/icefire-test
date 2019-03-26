package com.icefire.assigment.encrytdecryptmodule.services;

import com.icefire.assigment.encrytdecryptmodule.dao.UserRepository;
import com.icefire.assigment.encrytdecryptmodule.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User findById(long id) {
        if (id <= 0)
            return new User();
        return userRepository.findById(id).orElse(new User());
    }

    public User findByName(String name) {
        if (name == null)
            return new User();
        return userRepository.findByName(name).orElse(new User());
    }

    public void saveUser(User user) {
        if (user != null && user.getName() != null && user.getPassword() != null &&
                user.getSecKey() != null && user.getSecKey() != null) {
            userRepository.save(user);
        }
    }

    public User updateUser(User user) {
        if (user != null && user.getId() > 0 && userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        }else{
            return new User();
        }
    }

    public void deleteUserById(long id) {
        if (id > 0) {
            userRepository.deleteById(id);
        }
    }

    public boolean isUserExist(User user) {
        if (user != null)
            return userRepository.existsById(user.getId());
        else
            return false;
    }

    @Override
    public SecretKey generateSecretKey() {
        SecretKey secKey = null;
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128);
            secKey = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return secKey;
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}

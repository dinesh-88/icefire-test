package com.icefire.assigment.encrytdecryptmodule.controllers;

import com.icefire.assigment.encrytdecryptmodule.models.User;
import com.icefire.assigment.encrytdecryptmodule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

import static org.apache.commons.codec.binary.Hex.encodeHex;

@RestController
@RequestMapping("/auth")
public class LoginTokenController {
    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity login(@RequestBody final User user) {
        User selectedUser = userService.findByName(user.getName());
        SecretKey secKey = userService.generateSecretKey();
        byte[] encoded = secKey.getEncoded();
        char[] hex = encodeHex(encoded);
        if (selectedUser.getSecKey() == null) {
            selectedUser.setSecKey(String.valueOf(hex));

        }
        User userSend = userService.updateUser(selectedUser);
        userSend.setPassword(null);
        userSend.setSecKey(null);
        return new ResponseEntity<User>(userSend, HttpStatus.OK);
    }

}

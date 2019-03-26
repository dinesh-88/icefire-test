package com.icefire.assigment.encrytdecryptmodule.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icefire.assigment.encrytdecryptmodule.models.User;
import com.icefire.assigment.encrytdecryptmodule.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static org.apache.commons.codec.binary.Hex.encodeHex;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginTokenController.class)
public class LoginTokenControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void login() throws Exception {

        User testUser = new User();
        testUser.setName("dinesh");
        testUser.setPassword("123");
        given(userService.findByName("dinesh")).willReturn(testUser);
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey secKey = generator.generateKey();
        given(userService.generateSecretKey()).willReturn(secKey);

        byte[] encoded = secKey.getEncoded();
        char[] hex = encodeHex(encoded);
        testUser.setSecKey(String.valueOf(hex));
        given(userService.updateUser(testUser)).willReturn(testUser);

        mvc.perform(post("/auth/")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testUser))).andExpect(status().isOk());
    }
}
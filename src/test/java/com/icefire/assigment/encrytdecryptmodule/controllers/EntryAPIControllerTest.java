package com.icefire.assigment.encrytdecryptmodule.controllers;

import com.icefire.assigment.encrytdecryptmodule.models.Entry;
import com.icefire.assigment.encrytdecryptmodule.services.EntryService;
import com.icefire.assigment.encrytdecryptmodule.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;

@RunWith(SpringRunner.class)
@WebMvcTest(EntryAPIController.class)
public class EntryAPIControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private EntryService entryService;

    @Test
    public void getEntriesUsingUserId() throws Exception{
        Entry entry = new Entry();
        entry.setUserId(1);
        entry.setEntry("dddddada");
        entry.setEntryId(1);

        List<Entry> entryList = Arrays.asList(entry);

        given(entryService.findByUserId(1)).willReturn(entryList);
        mvc.perform(get("/entry/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].entry", is(entry.getEntry())));

    }

    @Test
    public void encryptEntry() throws Exception{
        Entry testEntry = new Entry();
        testEntry.setEntry("test");
        testEntry.setUserId(1);
        given(entryService.encryptEntry(testEntry)).willReturn(testEntry);
        mvc.perform(post("/entry/encrypt/")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(testEntry))).andExpect(status().isCreated());
    }

    @Test
    public void decryptEntry() throws Exception{
        Entry testEntry = new Entry();
        testEntry.setEntry("dadatea");
        testEntry.setUserId(1);
        Entry testDeEntry = new Entry();
        testDeEntry.setEntry("test");
        testDeEntry.setUserId(1);
        given(entryService.decryptEntry(testEntry)).willReturn(testDeEntry);
        mvc.perform(post("/entry/decrypt/")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(testEntry))).andExpect(status().isOk());
    }
}
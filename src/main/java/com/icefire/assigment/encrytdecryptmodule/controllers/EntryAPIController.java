package com.icefire.assigment.encrytdecryptmodule.controllers;

import com.icefire.assigment.encrytdecryptmodule.models.Entry;
import com.icefire.assigment.encrytdecryptmodule.services.EntryService;
import com.icefire.assigment.encrytdecryptmodule.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/entry")
public class EntryAPIController {
    public static final Logger logger = LoggerFactory.getLogger(EntryAPIController.class);

    @Autowired
    EntryService entryService;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEntriesUsingUserId(@PathVariable("id") long id) {
        logger.info("Fetching Entries with user id {}", id);
        List<Entry> entries = entryService.findByUserId(id);
        if (entries.isEmpty()) {
            logger.warn("Entry with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Entry with User id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Entry>>(entries, HttpStatus.OK);
    }



    @RequestMapping(value = "/encrypt", method = RequestMethod.POST)
    public ResponseEntity<?> encryptEntry(@RequestBody Entry entry) {
        logger.info("Encrypting Entry : {}", entry);
        Entry returnedEntry = entryService.encryptEntry(entry);
        return new ResponseEntity<Entry>(returnedEntry, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/decrypt", method = RequestMethod.POST)
    public ResponseEntity<?> decryptEntry(@RequestBody Entry entry) {
        logger.info("Decrypting Entry : {}", entry);
        Entry returnedEntry = entryService.decryptEntry(entry);
        return new ResponseEntity<Entry>(returnedEntry, HttpStatus.OK);
    }
}

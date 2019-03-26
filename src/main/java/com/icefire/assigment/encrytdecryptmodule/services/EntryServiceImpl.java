package com.icefire.assigment.encrytdecryptmodule.services;

import com.icefire.assigment.encrytdecryptmodule.controllers.EntryAPIController;
import com.icefire.assigment.encrytdecryptmodule.dao.EntryRepository;
import com.icefire.assigment.encrytdecryptmodule.models.Entry;
import com.icefire.assigment.encrytdecryptmodule.models.User;
import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

import static org.apache.commons.codec.binary.Hex.decodeHex;

@Service("EntryService")
public class EntryServiceImpl implements EntryService {

    private static final Logger logger = LoggerFactory.getLogger(EntryAPIController.class);
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    UserService userService;

    @Override
    public List<Entry> findAllEntries() {
        return (List<Entry>) entryRepository.findAll();
    }

    @Override
    public Entry findByEntryId(long id) {
        if (id <= 0) {
            return new Entry();
        }
        return entryRepository.findById(id).orElse(new Entry());
    }

    @Override
    public List<Entry> findByUserId(long id) {
        return entryRepository.findByUserId(id);
    }

    @Override
    public Entry saveEntry(Entry entry) {
        if (entry != null && entry.getEntry() != null) {
            return entryRepository.save(entry);
        }
        return new Entry();
    }

    @Override
    public Entry encryptEntry(Entry entry) {
        if (getEncryptDecryptValue(entry, encrypt(entry))) return new Entry();
        return entryRepository.save(entry);
    }

    @Override
    public Entry decryptEntry(Entry entry) {
        if (getEncryptDecryptValue(entry, decrypt(entry))) return new Entry();
        return entry;
    }

    @Override
    public void deleteByEntryId(long id) {
        entryRepository.deleteById(id);
    }

    @Override
    public void deleteAllEntries() {
        entryRepository.deleteAll();
    }

    @Override
    public boolean isEntryExist(Entry entry) {
        if (entry == null)
            return false;
        return entryRepository.existsById(entry.getEntryId());
    }

    private boolean getEncryptDecryptValue(Entry entry, String cipherValue) {
        if (entry == null || entry.getEntry() == null || cipherValue == null) {
            return true;
        }
        entry.setEntry(cipherValue);
        return false;
    }

    private String encrypt(Entry entry) {
        byte[] byteCipherText;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secKey = getSecretKey(entry.getUserId());

            cipher.init(Cipher.ENCRYPT_MODE, secKey);
            byteCipherText = cipher.doFinal(entry.getEntry().getBytes());
        } catch (Exception e) {
            logger.error("encryption Failed", e);
            return null;
        }
        return Base64.getEncoder().encodeToString(byteCipherText);
    }

    private String decrypt(Entry entry) {
        String decryptedData;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secKey = getSecretKey(entry.getUserId());
            cipher.init(Cipher.DECRYPT_MODE, secKey);
            decryptedData = new String(cipher.doFinal(Base64.getDecoder().decode(entry.getEntry())));
        } catch (Exception e) {
            logger.error("decryption Failed ", e);
            return null;
        }
        return decryptedData;
    }

    private SecretKey getSecretKey(long id) {
        User user = userService.findById(id);
        char[] hex = user.getSecKey().toCharArray();
        byte[] encoded = null;
        try {
            encoded = decodeHex(hex);
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(encoded, "AES");
    }
}

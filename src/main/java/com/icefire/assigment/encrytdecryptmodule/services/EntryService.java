package com.icefire.assigment.encrytdecryptmodule.services;

import com.icefire.assigment.encrytdecryptmodule.models.Entry;

import java.util.List;

public interface EntryService {
    List<Entry> findAllEntries();
    Entry findByEntryId(long id);
    List<Entry> findByUserId(long id);
    Entry saveEntry(Entry entry);
    Entry encryptEntry(Entry entry);
    Entry decryptEntry(Entry entry);
    void deleteByEntryId(long id);
    void deleteAllEntries();
    boolean isEntryExist(Entry entry);
}

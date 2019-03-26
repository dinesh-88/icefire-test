package com.icefire.assigment.encrytdecryptmodule.dao;

import com.icefire.assigment.encrytdecryptmodule.models.Entry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EntryRepository extends CrudRepository<Entry, Long> {
    List<Entry> findByUserId(long id);
}

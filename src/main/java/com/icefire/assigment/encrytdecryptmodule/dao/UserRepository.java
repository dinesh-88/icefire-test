package com.icefire.assigment.encrytdecryptmodule.dao;

import com.icefire.assigment.encrytdecryptmodule.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String Name);
}

package com.namber.passbookapi.dao;

import com.namber.passbookapi.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, String> {
    public List<User> findByUsername(String username);
}

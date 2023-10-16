package org.example.repository;

import org.example.entity.User;

import java.util.List;

public interface UserRepository {
    User get(Long id);
    void add(User user);
    void update(User user);
    void remove(User user);

    List<User> getAllUsers();
}

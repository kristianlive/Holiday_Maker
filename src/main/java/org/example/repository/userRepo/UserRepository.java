package org.example.repository.userRepo;

import org.example.entity.User;

import java.util.List;

public interface UserRepository {
    User get(int id);
    int add(User user);
    void update(User user);
    void remove(User user);

    List<User> getAllUsers();
}

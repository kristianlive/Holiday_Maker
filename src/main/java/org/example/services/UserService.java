package org.example.services;

import org.example.entity.User;
import org.example.repository.userRepo.UserRepository;

import java.util.Optional;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void findById(Long id) {
        System.out.println(userRepository.get(id));
    }

    public void addUser(User user) {
        userRepository.add(user);
        System.out.println("User added");
    }

    public void updateUser(User user) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.get((long) user.getId()));
        if (userOptional.isPresent()) {
            userRepository.update(user);

            System.out.println("User updated");
        } else {
            System.out.println("User not found");
        }

    }

    public void removeUser(Long id) {
        if (userRepository.get(id) == null) {
            System.out.println("User not found");
            return;
        }
        User user = userRepository.get(id);
        userRepository.remove(user);
        System.out.println("User removed");
    }

    public void getAllUsers() {
       var users = userRepository.getAllUsers();
        for (   var user: users) {
            System.out.println(user);
        }
        System.out.println("All users");
    }

    public void getUserByEmail() {
        System.out.println("User by email");
    }

    public void getUserByFirstName() {
        System.out.println("User by first name");
    }
}

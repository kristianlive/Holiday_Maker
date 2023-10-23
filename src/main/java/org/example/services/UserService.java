package org.example.services;


import org.example.entity.User;
import org.example.repository.userRepo.UserRepository;


import org.example.entity.CustomTrip;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private List<CustomTrip> customTrips;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void findById(int id) {
        System.out.println(userRepository.get(id));
    }

    public User getUser(int userId) {
        return userRepository.get(userId);
    }

    public void addUser(User user) {
        userRepository.add(user);
        System.out.println("User added");
    }

    public void updateUser(User user) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.get(user.getId()));
        if (userOptional.isPresent()) {
            userRepository.update(user);

            System.out.println("User updated");
        } else {
            System.out.println("User not found");
        }

    }

    public void removeUser(int id) {
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
        for (var user : users) {
            System.out.println(user);
        }
        System.out.println("All users");
    }
}

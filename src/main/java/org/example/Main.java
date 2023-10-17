package org.example;

import org.example.db.Database;
import org.example.entity.User;

import org.example.repository.userRepo.UserRepositoryImp;
import org.example.services.UserService;


public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.connectToDb();
        UserService userService = new UserService(new UserRepositoryImp());
        userService.findById(1L);
        userService.getAllUsers();
        userService.addUser(User.builder()
                .firstName("David")
                .lastName("Davidsson")
                .email("david.dav@example.com")
                .password("123456")
                .build());
        userService.getAllUsers();
        userService.updateUser(User.builder()
                .id(1)
                .firstName("David")
                .lastName("Reed")
                .email("david.reed@hotmail.com")
                .password("123456")
                .build());
        userService.getAllUsers();
        userService.removeUser(1L);
        userService.getAllUsers();


    }
}
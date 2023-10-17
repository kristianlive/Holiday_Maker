package org.example;

import org.example.db.Database;
import org.example.entity.User;
import org.example.repository.UserRepositoryImp;
import org.example.services.UserService;


public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.connectToDb();
        UserService userService = new UserService(new UserRepositoryImp());
        userService.findById(4L);
        userService.getAllUsers();
//        userService.addUser(User.builder()
//                .firstName("David")
//                .lastName("Davidsson")
//                .email("david.dav@example.com")
//                .password("123456")
//                .build());
        userService.getAllUsers();
        userService.updateUser(User.builder()
                .id(9)
                .firstName("David")
                .lastName("Reed")
                .email("david.refai@hotmail.com")
                .password("123456")
                .build());
        userService.getAllUsers();
        userService.removeUser(7L);
        userService.getAllUsers();


    }
}
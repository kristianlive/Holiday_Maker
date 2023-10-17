package org.example.services;

import org.example.db.Database;
import org.example.entity.User;
import org.example.repository.UserRepository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository;
    private Database db = Database.getInstance();



    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.createUserTable();
    }

    private void createUserTable(){
        try {
            var conn = db.connectToDb();
            String query = "CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id INT PRIMARY KEY,\n" +
                    "    first_name VARCHAR(255) NOT NULL,\n" +
                    "    last_name VARCHAR(255) NOT NULL,\n" +
                    "    email VARCHAR(255) NOT NULL,\n" +
                    "    password VARCHAR(255) NOT NULL\n" +
                    ");";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        }catch (SQLException ex){
            ex.printStackTrace();

    }
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

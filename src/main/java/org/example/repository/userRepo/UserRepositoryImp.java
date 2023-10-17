package org.example.repository.userRepo;

import org.example.db.Database;
import org.example.entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImp implements UserRepository {
    Database db = new Database();
Connection conn = db.connectToDb();
Statement stmt = null;
ResultSet rs = null;
    @Override
    public User get(Long id) {
        User user = null;
        try {
            conn = db.connectToDb();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM users WHERE id = " + id);
            while (rs.next()) {
                user = User.builder()
                        .id(rs.getInt("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .build();
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return user;
    }

    @Override
    public void add(User user) {
        try {
            String insertQuery = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());

        }
    }

    @Override
    public void update(User user) {

        try {
            String updateQuery = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setInt(5, user.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(User user) {
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM users WHERE id = " + user.getId());
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
      String sql = "SELECT * FROM users";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
               users.add(User.builder()
                       .id(rs.getInt("id"))
                       .firstName(rs.getString("first_name"))
                       .lastName(rs.getString("last_name"))
                       .email(rs.getString("email"))
                       .password(rs.getString("password"))
                       .build());
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return users;
    }

}

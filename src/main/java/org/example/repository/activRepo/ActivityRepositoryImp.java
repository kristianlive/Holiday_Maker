package org.example.repository.activRepo;

import org.example.db.Database;
import org.example.entity.Activity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepositoryImp implements ActivityRepository {
    Database db = new Database();
    Connection conn = db.connectToDb();
    Statement stmt = null;
    ResultSet rs = null;
    @Override
    public Activity get(Long id) {
        try {
            String selectQuery = "SELECT * FROM activities WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Activity activity = Activity.builder()
                        .id(resultSet.getLong("id"))
                        .title(resultSet.getString("title"))
                        .price(resultSet.getDouble("price"))
                        .build();
                return activity;
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void add(Activity activity) {
        try {
            String insertQuery = "INSERT INTO activities (title, price) VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, activity.getTitle());
            preparedStatement.setDouble(2, activity.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }

    @Override
    public void update(Activity activity) {
        try {
            String updateQuery = "UPDATE activities SET title = ?, price = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
            preparedStatement.setString(1, activity.getTitle());
            preparedStatement.setDouble(2, activity.getPrice());
            preparedStatement.setLong(3, activity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }

    @Override
    public void remove(Activity activity) {
        try {
            String deleteQuery = "DELETE FROM activities WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
            preparedStatement.setLong(1, activity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }

    @Override
    public List<Activity> getAllActivity() {
        List<Activity> activities = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM activities";
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Activity activity = Activity.builder()
                        .id(resultSet.getLong("id"))
                        .title(resultSet.getString("title"))
                        .price(resultSet.getDouble("price"))
                        .build();
                activities.add(activity);
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return activities;
    }
}

package org.example.repository.addonRepo;

import org.example.db.Database;
import org.example.entity.Addon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddonRepositoryImp implements AddonRepository {
    Database db = Database.getInstance();
    Connection conn = db.connectToDb();
    Statement stmt = null;
    ResultSet rs = null;


    @Override
    public Addon findById(Long id) {
       Addon addon = null;
        try {
            conn = db.connectToDb();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM addon WHERE id = " + id);
            while (rs.next()) {
                addon = Addon.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .price(rs.getDouble("price"))
                        .build();
            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return addon;
    }


    @Override
    public void create(Addon addon) {
        try {
            String insertQuery = "INSERT INTO addon (title, price) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, addon.getTitle());
                preparedStatement.setDouble(2, addon.getPrice());


                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());

        }
    }

    @Override
    public void update(Addon addon) {
        try {
            String updateQuery = "UPDATE addon SET title = ?, price = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, addon.getTitle());
                preparedStatement.setDouble(2, addon.getPrice());
                preparedStatement.setLong(3,addon.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(Addon addon) {
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM addon WHERE id = " + addon.getId());

        } catch (SQLIntegrityConstraintViolationException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }

    @Override
    public List<Addon> getAllAddon() {
        List<Addon> addons = new ArrayList<>();
        String sql = "SELECT * FROM addon";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                addons.add(Addon.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .price(rs.getDouble("price"))
                        .build());
            }

        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return addons;
    }
}

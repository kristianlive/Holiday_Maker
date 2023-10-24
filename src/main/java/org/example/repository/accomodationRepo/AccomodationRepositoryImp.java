package org.example.repository.accomodationRepo;

import org.example.db.Database;
import org.example.entity.Accomodation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccomodationRepositoryImp implements AccomodationRepository {
    Database db = new Database();
    Connection conn = db.connectToDb();
    Statement stmt = null;
    ResultSet rs = null;

    @Override
    public Accomodation get(Long id) {
        Accomodation accomodation = null;
        try {
            conn = db.connectToDb();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM accommodation WHERE id = " + id);
            if (rs.next()) {
                accomodation = Accomodation.builder()
                        .id(rs.getLong("id"))
                        .type(rs.getString("type"))
                        .address(rs.getString("address"))
                        .price(rs.getDouble("price"))
                        .build();
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return accomodation;
    }

    /*
        @Override
    public Accomodation get(Long id) {
        Accomodation accomodation = null;
        String selectQuery = "SELECT * FROM accommodation WHERE id = ?";
        try {
            conn = db.connectToDb();
            try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
                preparedStatement.setLong(1, id);
                rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    accomodation = Accomodation.builder()
                            .id(rs.getLong("id"))
                            .type(rs.getString("type"))
                            .address(rs.getString("address"))
                            .price(rs.getDouble("price"))
                            .build();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return accomodation;
    }
     */

    @Override
    public void add(Accomodation accomodation) {
        try {
            String insertQuery = "INSERT INTO accomodations (type, address, price) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
                preparedStatement.setString(2, accomodation.getType());
                preparedStatement.setString(3, accomodation.getAddress());
                preparedStatement.setDouble(4, accomodation.getPrice());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }

    @Override
    public void update(Accomodation accomodation) {
        try {
            String updateQuery = "UPDATE accomodations SET type = ?, address = ?, price = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setString(2, accomodation.getType());
                preparedStatement.setString(3, accomodation.getAddress());
                preparedStatement.setDouble(4, accomodation.getPrice());
                preparedStatement.setLong(5, accomodation.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }

    @Override
    public void remove(Accomodation accomodation) {
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM accomodations WHERE id = " + accomodation.getId());
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }

    @Override
    public List<Accomodation> getAllAccomodations() {
        List<Accomodation> accomodations = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM accommodation");
            while (rs.next()) {
                accomodations.add(Accomodation.builder()
                        .id(rs.getLong("id"))
                        // .tripId(rs.getObject("trip_id", Trip.class))
                        .type(rs.getString("type"))
                        .address(rs.getString("address"))
                        .price(rs.getDouble("price"))
                        .build());
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return accomodations;
    }
}

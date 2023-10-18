package org.example.repository.tripRepo;

import org.example.db.Database;
import org.example.entity.Trip;
import org.example.entity.TypeOfTrip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TripRepositoryImp implements TripRepository {
    Database db = new Database();
    Connection conn = db.connectToDb();
    Statement stmt = null;
    ResultSet rs = null;
    @Override
    public Trip get(Long id) {
        String sql = "SELECT * FROM trips WHERE trip_id = ?";
        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Trip trip = Trip.builder()
                        .id(rs.getLong("trip_id"))
                        .name(rs.getString("name"))
                        .type(TypeOfTrip.valueOf(rs.getString("type")))
                        .price(rs.getDouble("price"))
                        .build();
                return trip;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void add(Trip trip) {
        String sql = "INSERT INTO trips (name, price, type, accommodation_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, trip.getName());
            stmt.setDouble(2, trip.getPrice());
            stmt.setString(3, trip.getType().toString());
            stmt.setLong(4, trip.getAccommodation().getId());


            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating trip failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    trip.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating trip failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Trip trip) {
        String sql = "UPDATE trips SET name = ?, price = ?, type = ?, accommodation_id = ? WHERE trip_id = ?";
        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trip.getName());
            stmt.setDouble(2, trip.getPrice());
            stmt.setString(3, trip.getType().toString());
            stmt.setLong(4, trip.getAccommodation().getId());
            stmt.setLong(5, trip.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating trip failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void remove(Trip trip) {
        String sql = "DELETE FROM trips WHERE trip_id = ?";
        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, trip.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


   @Override
   public List<Trip> getAllTrips() {
       List<Trip> trips = new ArrayList<>();
       String sql = "SELECT * FROM trips";
       try (Connection conn = db.connectToDb();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
           while (rs.next()) {
               Trip trip = Trip.builder()
                       .id(rs.getLong("trip_id"))
                       .name(rs.getString("name"))
                       .type(TypeOfTrip.valueOf(rs.getString("type")))
                       .price(rs.getDouble("price"))
                       .accommodation(getAccommodationById(rs.getLong("accommodation_id")))
                       .build();

               trips.add(trip);
           }
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
       return trips;
   }

}

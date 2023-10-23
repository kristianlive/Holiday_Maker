package org.example.repository.tripRepo;

import org.example.db.Database;
import org.example.entity.Activity;
import org.example.entity.Addon;
import org.example.entity.Bookings;
import org.example.entity.CustomTrip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TripRepositoryImp implements TripRepository {
    Database db = new Database();
    Connection conn = db.connectToDb();
    Statement stmt = null;
    ResultSet rs = null;

    @Override
    public CustomTrip get(Long id) {
        String sql = "SELECT * FROM custom_trips WHERE trip_id = ?";
        CustomTrip customTrip = null;

        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customTrip = extractCustomTripFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return customTrip;
    }

    private CustomTrip extractCustomTripFromResultSet(ResultSet rs) throws SQLException {
        CustomTrip customTrip = new CustomTrip();
        customTrip.setId(rs.getLong("trip_id"));
        customTrip.setTotalPrice(rs.getDouble("totalprice"));

        List<Activity> activities = fetchActivitiesForCustomTrip(customTrip.getId());
        customTrip.setActivities(activities);

        List<Addon> addons = fetchAddonsForCustomTrip(customTrip.getId());
        customTrip.setAddons(addons);

        customTrip.setAccommodation(rs.getInt("accommodation_id"));
        customTrip.setDestination(rs.getInt("destination_id"));

        return customTrip;
    }

    private List<Activity> fetchActivitiesForCustomTrip(Long customTripId) {
        String sql = "SELECT * FROM trip_activities WHERE custom_trips_id = ?";
        List<Activity> activities = new ArrayList<>();

        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, customTripId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Activity activity = new Activity();
                activity.setId(rs.getLong("activity_id"));
                // Retrieve other activity properties from the result set if needed
                activities.add(activity);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return activities;
    }

    private List<Addon> fetchAddonsForCustomTrip(Long customTripId) {
        String sql = "SELECT * FROM trip_addons WHERE custom_trips_id = ?";
        List<Addon> addons = new ArrayList<>();

        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, customTripId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Addon addon = new Addon();
                addon.setId(rs.getLong("addon_id"));
                // Retrieve other addon properties from the result set if needed
                addons.add(addon);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return addons;
    }


    @Override
    public void add(CustomTrip customTrip) {
        String tripSql = "INSERT INTO custom_trips (totalprice, accommodation_id, destination_id) VALUES (?, ?, ?)";
        String activitySql = "INSERT INTO trip_activities (custom_trips_id, activity_id) VALUES (?, ?)";
        String addonSql = "INSERT INTO trip_addons (custom_trips_id, addon_id) VALUES (?, ?)";

        try (
                PreparedStatement tripStmt = conn.prepareStatement(tripSql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement activityStmt = conn.prepareStatement(activitySql);
                PreparedStatement addonStmt = conn.prepareStatement(addonSql)) {

            // Insert into trips table
            tripStmt.setDouble(1, customTrip.getTotalPrice());
            tripStmt.setLong(2, customTrip.getAccommodation());
            tripStmt.setLong(3, customTrip.getDestination());

            int affectedRows = tripStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating trip failed, no rows affected.");
            }

            try (ResultSet generatedKeys = tripStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customTrip.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating trip failed, no ID obtained.");
                }
            }

            // Insert activities into TripActivities table
            for (Activity activity : customTrip.getActivities()) {
                activityStmt.setLong(1, customTrip.getId());
                activityStmt.setLong(2, activity.getId());
                activityStmt.executeUpdate();
            }

            // Insert addons into TripAddons table
            for (Addon addon : customTrip.getAddons()) {
                addonStmt.setLong(1, customTrip.getId());
                addonStmt.setLong(2, addon.getId());
                addonStmt.executeUpdate();
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addBooking(int user_id, long custom_trips_id) {
        String bookingSql = "INSERT INTO bookings (user_id, custom_trips_id) VALUES (?, ?)";
        try {
            PreparedStatement bookingStmt = conn.prepareStatement(bookingSql);
            bookingStmt.setInt(1, user_id);
            bookingStmt.setLong(2, custom_trips_id);


            bookingStmt.executeUpdate();
            int affectedRows = bookingStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating trip failed, no rows affected.");
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }


    @Override
    public void update(CustomTrip customTrip) {
        String sql = "UPDATE custom_trips SET totalprice = ?, accommodation_id = ?, destination_id = ? WHERE trip_id = ?";
        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, customTrip.getTotalPrice());
            stmt.setLong(2, customTrip.getAccommodation());
            stmt.setLong(3, customTrip.getDestination());
            stmt.setLong(4, customTrip.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating trip failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void remove(CustomTrip customTrip) {
        String sql = "DELETE FROM trips WHERE trip_id = ?";
        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, customTrip.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<CustomTrip> getAllTrips() {
        List<CustomTrip> customTrips = new ArrayList<>();
        String sql = "SELECT * FROM custom_trips";
        try (Connection conn = db.connectToDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CustomTrip customTrip = CustomTrip.builder()
                        .id(rs.getLong("trip_id"))
                        .totalPrice(rs.getDouble("totalprice"))
                        .build();
                customTrips.add(customTrip);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customTrips;
    }

}

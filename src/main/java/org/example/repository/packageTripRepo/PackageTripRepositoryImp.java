package org.example.repository.packageTripRepo;

import org.example.db.Database;
import org.example.entity.PackageTrip;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageTripRepositoryImp implements PackageTripRepository {
    Database db = new Database();
    Connection conn = db.connectToDb();
    Statement stmt = null;
    ResultSet rs = null;

    @Override
    public PackageTrip get(Long id) {
        String sql = "SELECT * FROM package_trips WHERE package_trips_id = ?";
        try (Connection conn = db.connectToDb();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PackageTrip packagetrip = new PackageTrip();
                packagetrip.setId(rs.getLong("package_trips_id"));
                return packagetrip;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<PackageTrip> getAllPackageTrips() {
        List<PackageTrip> packagetrips = new ArrayList<>();
        String sql = "SELECT * FROM package_trips";
        try (Connection conn = db.connectToDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PackageTrip packagetrip = PackageTrip.builder()
                        .id(rs.getLong("id"))
                        .destination(rs.getString("destination"))
                        .description(rs.getString("description"))
                        .accommodation_id(rs.getInt("accommodation_id"))
                        .addon_id(rs.getInt("addon_id"))
                        .activity_id(rs.getInt("activity_id"))
                        .price(rs.getDouble("price"))
                        .build();

                packagetrips.add(packagetrip);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return packagetrips;
    }
}

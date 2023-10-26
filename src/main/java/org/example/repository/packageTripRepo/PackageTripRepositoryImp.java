package org.example.repository.packageTripRepo;

import org.example.db.Database;
import org.example.entity.PackageTrip;
import org.example.entity.PackageTripDetails;


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
    public List<PackageTripDetails> getAllPackageTrips() {
        List<PackageTripDetails> packageTrips = new ArrayList<>();
        String sql = "SELECT pt.id, pt.destination, pt.description, a.type AS accommodation, ad.title AS addons, act.title AS activity, pt.price " +
                "FROM package_trips pt " +
                "LEFT JOIN accommodation a ON pt.accommodation_id = a.id " +
                "LEFT JOIN addon ad ON pt.addon_id = ad.id " +
                "LEFT JOIN activity act ON pt.activity_id = act.id";
        try (Connection conn = db.connectToDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PackageTripDetails packageTrip = PackageTripDetails.builder()
                        .bookingsId(rs.getLong("id"))
                        .destination(rs.getString("destination"))
                        .description(rs.getString("description"))
                        .accommodationType(rs.getString("accommodation"))
                        .addonTitle(rs.getString("addons"))
                        .activityTitle(rs.getString("activity"))
                        .totalprice(rs.getDouble("price"))
                        .build();

                packageTrips.add(packageTrip);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return packageTrips;
    }


}

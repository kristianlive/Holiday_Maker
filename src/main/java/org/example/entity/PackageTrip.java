package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageTrip {
    private int packageId;
    private String name;
    private String description;
    private Accommodation accommodation;
    private List<Activity> activities;
    private List<Addon> addons;
    private double price;

    public static List<PackageTrip> getAllPackageTrips(Connection connection) throws SQLException {
        List<PackageTrip> packageTrips = new ArrayList<>();
        String sql = "SELECT * FROM package_trips";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                PackageTrip packageTrip = new PackageTripBuilder()
                        .packageId(resultSet.getInt("package_id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .build();
                packageTrips.add(packageTrip);
            }
        }
        return packageTrips;
    }

    public static void displayAllPackageTrips(List<PackageTrip> packageTrips) {
        for (PackageTrip trip : packageTrips) {
            System.out.println("Package ID: " + trip.getPackageId());
            System.out.println("Name: " + trip.getName());
            System.out.println("Description: " + trip.getDescription());
            System.out.println("Price: " + trip.getPrice());
            System.out.println("-------------------------------");
        }
    }
}


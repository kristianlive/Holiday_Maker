package org.example.repository.destinationRepo;

import org.example.db.Database;
import org.example.entity.Destination;
import org.example.entity.Trip;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DestinationRepositoryImp implements DestinationRepository{

    Database db = new Database();

    @Override
    public List<Destination> getAllDestinations() {
        List<Destination> destinations = new ArrayList<>();
        String sql = "SELECT * FROM destination";
        try (Connection conn = db.connectToDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Destination destination = Destination.builder()
                        .id(rs.getInt("id"))
                        .city(rs.getString("city"))
                        .price(rs.getDouble("price"))
                        .build();

                destinations.add(destination);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return destinations;
    }
}

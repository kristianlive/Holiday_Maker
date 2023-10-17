package org.example.repository.bookingRepo;

import org.example.db.Database;
import org.example.entity.Bookings;

import java.sql.*;
import java.util.List;

public class BookingRepositoryImp implements BookingRepository{

    Database db = new Database();
    Connection conn = db.connectToDb();
    Statement stmt = null;
    ResultSet rs = null;
    @Override
    public Bookings get(int id) {
        return null;
    }

    @Override
    public Bookings save(Bookings booking) {
        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO bookings (trip_id, user_id) VALUES (?, ?)")) {
            preparedStatement.setLong(1, booking.getTripId().getId());
            preparedStatement.setLong(2, booking.getUserId().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // You may want to handle or rethrow the exception as needed
        }
        return booking;
    }


    @Override
    public void update(Bookings booking) {

        try {
            String updateQuery = "UPDATE bookings SET trip_id = ?, user_id = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setLong(1, booking.getTripId().getId());
                preparedStatement.setLong(2, booking.getUserId().getId());
                preparedStatement.setLong(3, booking.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // You may want to handle or rethrow the exception as needed
        }

    }

    @Override
    public void remove(Bookings booking) {
        try {
            String deleteQuery = "DELETE FROM bookings WHERE id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery)) {
                preparedStatement.setLong(1, booking.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // You may want to handle or rethrow the exception as needed
        }

    }

    @Override
    public List<Bookings> getAllBookings() {
        return null;
    }
}

package org.example.repository.bookingRepo;

import org.example.db.Database;
import org.example.entity.Bookings;

import java.sql.*;
import java.util.ArrayList;
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
    public List<Bookings> findByLastName(String lastName) {
        List<Bookings> bookingsList = new ArrayList<>();
        String sql = "SELECT b.* FROM bookings b JOIN users u ON b.user_id = u.id WHERE u.last_name = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, lastName);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                // Skapa ett Bookings-objekt baserat på resultatet och lägg till det i listan
                // Obs: du kommer att behöva komplettera detta med att sätta andra attribut från din `Bookings` klass.
                Bookings booking = new Bookings();
                booking.setId(rs.getLong("id"));
                //... Sätt andra attribut här

                bookingsList.add(booking);
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return bookingsList;
    }


    @Override
    public List<Bookings> getAllBookings() {
        return null;
    }
}

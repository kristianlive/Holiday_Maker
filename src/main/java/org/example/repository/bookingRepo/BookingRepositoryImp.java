package org.example.repository.bookingRepo;

import org.example.db.Database;
import org.example.entity.Bookings;
import org.example.entity.PackageTrip;
import org.example.entity.Trip;
import org.example.entity.User;
import org.example.repository.packageTripRepo.PackageTripRepositoryImp;
import org.example.repository.tripRepo.TripRepositoryImp;
import org.example.services.PackageTripsServices;
import org.example.services.TripService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryImp implements BookingRepository{

    Database db = new Database();
    Connection conn = db.connectToDb();
    Statement stmt = null;
    ResultSet rs = null;

    TripService tripService = new TripService(new TripRepositoryImp());
    PackageTripsServices packageTripService = new PackageTripsServices(new PackageTripRepositoryImp());
    @Override
    public Bookings get(int id) {
        return null;
    }

    public boolean save(Bookings booking) {
        boolean isSuccess = false;
        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO bookings (user_id, custom_trips_id, package_trips_id) VALUES (?, ?, ?)")) {
            preparedStatement.setLong(1, booking.getUserId().getId());

            // Handle nullable fields
            Integer customTripId = booking.getCustomTripId();
            Integer packageTripId = booking.getPackageTripId();

            if (customTripId != null) {
                preparedStatement.setInt(2, customTripId);
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }

            if (packageTripId != null) {
                preparedStatement.setInt(3, packageTripId);
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }

            int rowsAffected = preparedStatement.executeUpdate();
            isSuccess = rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return isSuccess;
    }




    @Override
    public void update(Bookings booking) {
        try {
            String updateQuery = "UPDATE bookings SET user_id = ?, custom_trips_id = ?, package_trips_id = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setLong(1, booking.getUserId().getId());
                preparedStatement.setLong(2, booking.getCustomTripId());
                preparedStatement.setLong(3, booking.getPackageTripId());
                preparedStatement.setLong(4, booking.getId());


                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Handle the exception as needed
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
    public List<Bookings> getAllBookingsFromUser(User user) {
        List<Bookings> bookingsList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM bookings WHERE user_id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
                preparedStatement.setLong(1, user.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    int customTripId = resultSet.getInt("custom_trips_id");
                    int packageTripId = resultSet.getInt("package_trips_id");

                    // Assuming you have appropriate constructors for Trip classes
                   /* tripService.getTrip(customTripId);
                    packageTripService.getPackageTrip(packageTripId);*/

                    Bookings booking = new Bookings(id, user, customTripId, packageTripId);
                    bookingsList.add(booking);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Handle the exception as needed
        }
        return bookingsList;
    }

}

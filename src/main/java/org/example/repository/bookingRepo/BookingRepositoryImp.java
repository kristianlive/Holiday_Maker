package org.example.repository.bookingRepo;

import org.example.db.Database;
import org.example.entity.*;
import org.example.repository.packageTripRepo.PackageTripRepositoryImp;
import org.example.repository.tripRepo.TripRepositoryImp;
import org.example.services.PackageTripsServices;
import org.example.services.TripService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryImp implements BookingRepository {

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

    @Override
    public boolean addCustomTrip(Bookings booking) {
        boolean isSuccess = false;
        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO bookings (user_id, custom_trips_id) VALUES (?, ?)")) {
            preparedStatement.setLong(1, booking.getUserId().getId());

            Integer customTripId = booking.getCustomTripId();
            preparedStatement.setInt(2, customTripId);

            int rowsAffected = preparedStatement.executeUpdate();
            isSuccess = rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return isSuccess;
    }

    @Override
    public boolean addPackageTripToBooking(Bookings booking) {
        boolean isSuccess = false;
        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO bookings (user_id, package_trips_id) VALUES (?, ?)")) {
            preparedStatement.setLong(1, booking.getUserId().getId());
            int packageTripId = booking.getPackageTripId();
            preparedStatement.setInt(2, packageTripId);

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
    public List<CustomTrip> getCustomTripDetailsForUser(int userId) {
        List<CustomTrip> customTripDetailsList = new ArrayList<>();
        try {
            String query = "SELECT c.trip_id, a.type, d.city, " +
                    "(SELECT GROUP_CONCAT(DISTINCT ac.title SEPARATOR ', ') FROM trip_activities ta JOIN activity ac ON ac.id = ta.activity_id WHERE ta.custom_trips_id = c.trip_id) AS activity_titles, " +
                    "(SELECT GROUP_CONCAT(DISTINCT ad.title SEPARATOR ', ') FROM trip_addons tad JOIN addon ad ON ad.id = tad.addon_id WHERE tad.custom_trips_id = c.trip_id) AS addon_titles, " +
                    "c.totalprice " +
                    "FROM bookings b " +
                    "JOIN custom_trips c ON b.custom_trips_id = c.trip_id " +
                    "JOIN accommodation a ON c.accommodation_id = a.id " +
                    "JOIN destination d ON c.destination_id = d.id " +
                    "WHERE b.user_id = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int tripId = resultSet.getInt("trip_id");
                    String accommodationType = resultSet.getString("type");
                    String city = resultSet.getString("city");
                    String activityTitles = resultSet.getString("activity_titles");
                    String addonTitles = resultSet.getString("addon_titles");
                    double totalprice = resultSet.getDouble("totalprice");

                    // Parse activityTitles and addonTitles into lists of Activity and Addon objects
                    List<Activity> activities = parseActivityTitles(activityTitles);
                    List<Addon> addons = parseAddonTitles(addonTitles);

                    CustomTrip customTripDetails = new CustomTrip(tripId, accommodationType, city, activities, addons, totalprice);
                    customTripDetailsList.add(customTripDetails);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Handle the exception as needed
        }
        return customTripDetailsList;
    }

    public List<PackageTrip> getPackageTripDetailsForUser(int userId) {
        List<PackageTrip> packageTripList = new ArrayList<>();
        try {
            String query = "SELECT b.id as bookings_id, pt.description, a.title as addon_title, ac.type as accommodation_type, act.title as activity_title, pt.destination, pt.price as totalprice " +
                    "FROM bookings b " +
                    "LEFT JOIN package_trips pt ON b.package_trips_id = pt.id " +
                    "LEFT JOIN addon a ON pt.addon_id = a.id " +
                    "LEFT JOIN accommodation ac ON pt.accommodation_id = ac.id " +
                    "LEFT JOIN activity act ON pt.activity_id = act.id " +
                    "WHERE b.user_id = ? AND b.custom_trips_id IS NULL";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int bookingsId = resultSet.getInt("bookings_id");
                    String description = resultSet.getString("description");
                    String addonTitle = resultSet.getString("addon_title");
                    String accommodationType = resultSet.getString("accommodation_type");
                    String activityTitle = resultSet.getString("activity_title");
                    String destination = resultSet.getString("destination");
                    double totalprice = resultSet.getDouble("totalprice");

                    Addon addon = parseAddonTitle(addonTitle);
                    Accomodation accommodation = parseAccommodationType(accommodationType);
                    Activity activity = parseActivityTitle(activityTitle);

                    PackageTrip packageTrip = new PackageTrip((long) bookingsId, description, addon, accommodation, activity, destination, totalprice);
                    packageTripList.add(packageTrip);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            // Handle the exception as needed
        }
        return packageTripList;
    }

    // Implement the parse methods for Activity, Addon, and Accommodation titles here

    // ... (other methods)
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

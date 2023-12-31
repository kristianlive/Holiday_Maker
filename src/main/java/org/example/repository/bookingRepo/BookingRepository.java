package org.example.repository.bookingRepo;

import org.example.entity.Bookings;
import org.example.entity.CustomTripDetails;
import org.example.entity.PackageTripDetails;
import org.example.entity.User;

import java.util.List;

public interface BookingRepository {
    Bookings get(int id);
    boolean addCustomTrip(Bookings booking);
    void update(Bookings booking);
    void remove(Bookings booking);

    boolean addPackageTripToBooking(Bookings booking);

    List<CustomTripDetails> getCustomTripDetailsForUser(int userId);
    List<PackageTripDetails> getPackageTripDetailsForUser(int userId);
}

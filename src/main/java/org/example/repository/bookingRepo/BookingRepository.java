package org.example.repository.bookingRepo;

import org.example.entity.Bookings;
import org.example.entity.User;

import java.util.List;

public interface BookingRepository {
    Bookings get(int id);
    boolean save(Bookings booking);
    void update(Bookings booking);
    void remove(Bookings booking);

    List<Bookings> getAllBookingsFromUser(User user);
}

package org.example.repository.bookingRepo;

import org.example.entity.Bookings;

import java.util.List;

public interface BookingRepository {
    Bookings get(int id);
    Bookings save(Bookings booking);
    void update(Bookings booking);
    void remove(Bookings booking);

    List<Bookings> getAllBookings();
}

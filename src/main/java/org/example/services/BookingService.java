package org.example.services;

import org.example.entity.Bookings;
import org.example.entity.Trip;
import org.example.entity.User;
import org.example.repository.bookingRepo.BookingRepository;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final BookingRepository bookingRepository;
    List<Bookings> userCart = new ArrayList<>();

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Add a trip to the cart
    public void addToCart(Trip trip, User user) {
        Bookings booking = new Bookings();
        booking.setTripId(trip);
        booking.setUserId(user);
        userCart.add(booking);
    }

    public void removeFromCart(Bookings booking) {
        userCart.remove(booking);
    }

    // Proceed to payment
    public void proceedToPayment() {
        for (Bookings booking : userCart) {
            bookingRepository.save(booking);
        }
        userCart.clear();
    }
    public List<Bookings> findBookingsByUserLastName(String lastName) {
        return bookingRepository.findByLastName(lastName);
    }






}


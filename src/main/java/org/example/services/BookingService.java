package org.example.services;

import org.example.entity.Bookings;
import org.example.entity.Trip;
import org.example.entity.User;
import org.example.repository.bookingRepo.BookingRepository;

import java.awt.print.Book;
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
    public void addToCart(int choice, User user) {
        System.out.println("Add to cart method");
        Integer packageTripId = null;


        packageTripId = choice;

        // Create booking only if packageTripId is non-null

        Bookings booking = Bookings.builder()
                .userId(user)
                .packageTripId(packageTripId)
                .build();

        bookingRepository.save(booking);
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


}


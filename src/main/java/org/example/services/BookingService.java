package org.example.services;

import org.example.entity.Bookings;
import org.example.entity.CustomTripDetails;
import org.example.entity.User;
import org.example.repository.bookingRepo.BookingRepository;

import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final BookingRepository bookingRepository;
    List<Bookings> userCart = new ArrayList<>();

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Add a trip to the cart
    public void addToCart(int choice, int userId) {
        System.out.println("Add to cart method");

        Bookings booking = Bookings.builder()
                .userId(userId)
                .packageTripId(choice)
                .build();

        boolean successfullyAddedPackageTrip = bookingRepository.addPackageTripToBooking(booking);
        if (successfullyAddedPackageTrip) {
            System.out.println("Successfully created packageTrip");
        } else {
            System.out.println("Could not create packageTrip");
        }
    }


    public void removeFromCart(Bookings booking) {
        userCart.remove(booking);
    }

    // Proceed to payment
    public void proceedToPayment() {
        for (Bookings booking : userCart) {
            /*bookingRepository.save(booking);*/
        }
        userCart.clear();
    }

    public List<CustomTripDetails> getAllCustomTripsFromUser(int userId) {
        List<CustomTripDetails> customTripDetailsList = bookingRepository.getCustomTripDetailsForUser(userId);
        return customTripDetailsList;
    }

}


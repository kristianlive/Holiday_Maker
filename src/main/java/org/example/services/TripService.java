package org.example.services;

import org.example.entity.Trip;
import org.example.entity.User;
import org.example.repository.tripRepo.TripRepositoryImp;


import java.util.Optional;

public class TripService {

    private TripRepositoryImp tripRepositoryImp;

    public TripService(TripRepositoryImp tripRepositoryImp) {
        this.tripRepositoryImp = tripRepositoryImp;
    }

    public void addTrip(Trip trip) {
        tripRepositoryImp.add(trip);
        System.out.println("trip added");
    }

    public void getTrip(Long id) {
        System.out.println(tripRepositoryImp.get(id));
    }

    public void getAllTrips() {
        var trips = tripRepositoryImp.getAllTrips();
        System.out.println("All Trips: ");
        for (var trip : trips) {
            System.out.println(trip);
        }

    }
    public void removeTrip(Long id) {
        if (tripRepositoryImp.get(id) == null) {
            System.out.println("Trip not found");
            return;
        }
        Trip trip = tripRepositoryImp.get(id);
        tripRepositoryImp.remove(trip);
        System.out.println("Trip removed");
    }
  public void updatetrip(Trip trip) {
        Optional<Trip> tripOptional = Optional.ofNullable(tripRepositoryImp.get((long) trip.getId()));
        if (tripOptional.isPresent()) {
            tripRepositoryImp.update(trip);

            System.out.println("trip updated");
        } else {
            System.out.println("trip not found");
        }
    }


}

package org.example.services;

import org.example.entity.CustomTrip;
import org.example.repository.tripRepo.TripRepositoryImp;


import java.util.Optional;

public class TripService {

    private TripRepositoryImp tripRepositoryImp;

    public TripService(TripRepositoryImp tripRepositoryImp) {
        this.tripRepositoryImp = tripRepositoryImp;
    }

    public void addTrip(CustomTrip customTrip) {
        tripRepositoryImp.add(customTrip);
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

    public void addBooking(int user_id, long custom_trips_id) {
        tripRepositoryImp.addBooking(user_id, custom_trips_id);
    }
    public void removeTrip(Long id) {
        if (tripRepositoryImp.get(id) == null) {
            System.out.println("Trip not found");
            return;
        }
        CustomTrip customTrip = tripRepositoryImp.get(id);
        tripRepositoryImp.remove(customTrip);
        System.out.println("Trip removed");
    }
  public void updatetrip(CustomTrip customTrip) {
        Optional<CustomTrip> tripOptional = Optional.ofNullable(tripRepositoryImp.get((long) customTrip.getId()));
        if (tripOptional.isPresent()) {
            tripRepositoryImp.update(customTrip);

            System.out.println("trip updated");
        } else {
            System.out.println("trip not found");
        }
    }


}

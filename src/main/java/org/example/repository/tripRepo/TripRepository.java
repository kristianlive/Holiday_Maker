package org.example.repository.tripRepo;

import org.example.entity.Trip;

import java.util.List;

public interface TripRepository {
    Trip get(Long id);
    void add(Trip trip);
    void update(Trip trip);
    void remove(Trip trip);

    List<Trip> getAllTrips();
}

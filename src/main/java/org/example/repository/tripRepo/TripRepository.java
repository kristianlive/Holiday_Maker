package org.example.repository.tripRepo;

import org.example.entity.CustomTrip;



import java.util.List;

public interface TripRepository {
    CustomTrip get(Long id);
    void add(CustomTrip customTrip);
    void update(CustomTrip customTrip);
    void remove(CustomTrip customTrip);

    List<CustomTrip> getAllTrips();
}

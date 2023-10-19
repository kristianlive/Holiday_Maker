package org.example.repository.packageTripRepo;

import org.example.entity.PackageTrip;
import org.example.entity.Trip;



import java.util.List;

public interface PackageTripRepository {
    PackageTrip get(Long id);
    List<PackageTrip> getAllPackageTrips();
}
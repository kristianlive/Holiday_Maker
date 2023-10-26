package org.example.repository.packageTripRepo;

import org.example.entity.PackageTrip;
import org.example.entity.PackageTripDetails;


import java.util.List;

public interface PackageTripRepository {
    PackageTrip get(Long id);
    List<PackageTripDetails> getAllPackageTrips();
}
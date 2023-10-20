package org.example.services;

import org.example.entity.PackageTrip;
import org.example.repository.packageTripRepo.PackageTripRepositoryImp;


public class PackageTripsServices {

    private PackageTripRepositoryImp packageTripRepositoryImp;

    public PackageTripsServices(PackageTripRepositoryImp packageTripRepositoryImp) {this.packageTripRepositoryImp = packageTripRepositoryImp; }

    public void getPackageTrip(Long id) {System.out.println(packageTripRepositoryImp.get(id));}

    public void getAllPackageTrips() {
        var packageTrips = packageTripRepositoryImp.getAllPackageTrips();
        System.out.println("All Package Trips: ");
        for (var packageTrip : packageTrips) {
            System.out.println(packageTrip);
        }
    }
}

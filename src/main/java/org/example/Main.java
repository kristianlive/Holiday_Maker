package org.example;

import org.example.db.Database;



public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.connectToDb();

        TripPlanner tripPlanner = new TripPlanner();
        tripPlanner.run();



       // AccomodationService accomodationService = new AccomodationService();


    }
}
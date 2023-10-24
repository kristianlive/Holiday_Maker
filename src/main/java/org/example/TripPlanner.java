package org.example;

import org.example.entity.*;
import org.example.repository.accomodationRepo.AccomodationRepositoryImp;
import org.example.repository.activRepo.ActivityRepositoryImp;
import org.example.repository.addonRepo.AddonRepositoryImp;
import org.example.repository.bookingRepo.BookingRepositoryImp;
import org.example.repository.destinationRepo.DestinationRepositoryImp;
import org.example.repository.packageTripRepo.PackageTripRepositoryImp;
import org.example.repository.tripRepo.TripRepositoryImp;
import org.example.repository.userRepo.UserRepositoryImp;
import org.example.services.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TripPlanner {

    private Scanner scanner;

    public TripPlanner() {
        this.scanner = new Scanner(System.in);
    }

    UserService userService = new UserService(new UserRepositoryImp());
    PackageTripsServices packageTripsService = new PackageTripsServices(new PackageTripRepositoryImp());
    BookingService bookingService = new BookingService(new BookingRepositoryImp());
    TripService tripService = new TripService(new TripRepositoryImp());
    DestinationService destinationService = new DestinationService(new DestinationRepositoryImp());
    AddonService addonService = new AddonService(new AddonRepositoryImp());
    ActivityService activityService = new ActivityService(new ActivityRepositoryImp());
    AccomodationService accomodationService = new AccomodationService(new AccomodationRepositoryImp());

    public void run() {
        System.out.println("Welcome to the Trip Planner!");
        System.out.println("-----------------------------");

        /*registerUser();*/
        mainMenu();
    }

    private void registerUser() {
        System.out.println("Please register before proceeding:");

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Call the method to store the user information in the database.
        // saveUserInfo(firstName, lastName, email, password);
        userService.addUser(User.builder().firstName(firstName).lastName(lastName).email(email).password(password).build());
        System.out.println("Registration Successful!");
        System.out.println("-----------------------------");
        userService.getAllUsers();
    }

    private void mainMenu() {
        while (true) {
            System.out.println("Choose the type of trip:");
            System.out.println("1. Book a package trip");
            System.out.println("2. Book a custom trip, choosing individually");
            System.out.println("3. Show all bookings");
            System.out.println("0. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    packageMenu();
                    break;
                case 2:
                    customMenu();
                    break;
                case 3:
                    bookingMenu();
                case 0:
                    System.out.println("Thank you for using Trip Planner. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void bookingMenu() {
    // Get all bookings associated with the user
       List<CustomTripDetails> customTripDetailsList =  bookingService.getAllCustomTripsFromUser(30);
        // Print each CustomTripDetails object on a new line
        for (CustomTripDetails customTripDetails : customTripDetailsList) {
            System.out.println(customTripDetails);
        }
    }

    private User getUserFromInput() {

        System.out.print("Enter user ID: ");
        int userId = Integer.parseInt(scanner.nextLine());

        // Use UserService to get the user by ID
        User user = userService.getUser(userId);


        if (user == null) {
            System.out.println("User not found. Please try again.");
            // Retry getting the user if not found
            return getUserFromInput();
        }
        System.out.println("Hello " + user.getFirstName());
        return user;
    }

    private void packageMenu() {
        System.out.println("-----------------------------");
        User user = getUserFromInput();
        System.out.println("-----------------------------");
        System.out.println("Choose a package trip, 1-10:");
        System.out.println("0 to return to main menu");

        packageTripsService.getAllPackageTrips();

        int choice = Integer.parseInt(scanner.nextLine());

        if (choice >= 1 && choice <= 10) {
            addPackageTripToBooking(choice, user);
        } else if (choice == 0) {
            mainMenu();
        } else {
            System.out.println("Invalid choice. Please try again.");
            packageMenu();
        }
    }

    private void addPackageTripToBooking(int choice, User user) {
        bookingService.addToCart(choice, user);
    }

    private void customMenu() {
        System.out.println("-----------------------------");
        User user = getUserFromInput();
        // Get destination ID from user input
        System.out.print("Enter destination ID: ");
        int destinationId = addDestinationToCustomTrip();

        // Get accommodation ID from user input
        System.out.print("Enter accommodation ID: ");
        int accommodationId = addAccommodationToCustomTrip();
        List<Addon> addons = addAddonToCustomTrip();
        List<Activity> activities = addActivityToCustomTrip();

        double addonTotalPrice = 0.0;
        double activityTotalPrice = 0.0;

        for (Addon addon : addons) {
            double addonPrice = addon.getPrice();
            addonTotalPrice += addonPrice;
        }
        for (Activity activity: activities) {
            double activityPrice = activity.getPrice();
            activityTotalPrice += activityPrice;
        }


        double destinationPrice = destinationService.findPriceById(destinationId);
        double accommodationPrice = accomodationService.findPriceById(accommodationId);

        double totalPrice = calculateTotalPrice(destinationPrice, accommodationPrice, addonTotalPrice, activityTotalPrice);

        // Create CustomTrip object
        CustomTrip customTrip = CustomTrip.builder()
                .destination(destinationId)
                .accommodation(accommodationId)
                .activities(activities)
                .addons(addons)
                .totalPrice(totalPrice)
                .build();


        tripService.addTrip(customTrip);
        tripService.addBooking(user.getId(), customTrip.getId());
        System.out.println("Trip created successfully!");

        System.out.println("0. Go back to the main menu");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                // Handle other options related to the created trip if needed
                break;
            case 0:
                mainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                customMenu();
        }
    }

    private List<Activity> addActivityToCustomTrip() {
        List<Activity> activitiesArray = new ArrayList<>();
        boolean addMoreActivities = true;

        while (addMoreActivities) {
            System.out.println("All activities:");
            activityService.getAllActivities();

            System.out.println("Choose an activity, input id:");
            int choiceOfActivity = Integer.parseInt(scanner.nextLine());

            Activity activity = activityService.findById((long) choiceOfActivity);
            activitiesArray.add(activity);

            System.out.println("Activity added to the custom trip.");
            System.out.println("Do you want to add more activities? (yes/no):");
            String userInput = scanner.nextLine().toLowerCase();

            if (!userInput.equals("yes")) {
                addMoreActivities = false;
            }
        }

        return activitiesArray;
    }

    private List<Addon> addAddonToCustomTrip() {
        List<Addon> addonsArray = new ArrayList<>();
        boolean addMoreAddons = true;

        while (addMoreAddons) {
            System.out.println("All addons:");
            addonService.getAllAddons();

            System.out.println("Choose an addon, input id:");
            int choiceOfAddon = Integer.parseInt(scanner.nextLine());

            Addon addon = addonService.findById((long) choiceOfAddon);
            addonsArray.add(addon);


            System.out.println("Addon added to the custom trip.");
            System.out.println("Do you want to add more addons? (yes/no):");
            String userInput = scanner.nextLine().toLowerCase();

            if (!userInput.equals("yes")) {
                addMoreAddons = false;
            }
        }
        System.out.println(addonsArray);
        return addonsArray;
    }


    private int addAccommodationToCustomTrip() {
        System.out.println("All accommodations");
        accomodationService.getAllAccomodations();
        return Integer.parseInt(scanner.nextLine());
    }

    private int addDestinationToCustomTrip() {
        System.out.println("Choose a destination");
        destinationService.getAllDestinations();
        return Integer.parseInt(scanner.nextLine());
    }

    private double calculateTotalPrice(double destinationPrice, double accommodationPrice, double addonPrice, double activityPrice) {
        return destinationPrice + accommodationPrice + addonPrice + activityPrice;
    }
}

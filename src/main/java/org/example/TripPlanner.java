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

    private int currentUser;
    private final Scanner scanner = new Scanner(System.in);

    private final UserService userService = new UserService(new UserRepositoryImp());
    private final PackageTripsServices packageTripsService = new PackageTripsServices(new PackageTripRepositoryImp());
    private final BookingService bookingService = new BookingService(new BookingRepositoryImp());
    private final TripService tripService = new TripService(new TripRepositoryImp());
    private final DestinationService destinationService = new DestinationService(new DestinationRepositoryImp());
    private final AddonService addonService = new AddonService(new AddonRepositoryImp());
    private final ActivityService activityService = new ActivityService(new ActivityRepositoryImp());
    private final AccomodationService accomodationService = new AccomodationService(new AccomodationRepositoryImp());

    public void run() {
        displayWelcomeMessage();
        registerUser();
        mainMenu();
    }

    private void displayWelcomeMessage() {
        System.out.println("Welcome to the Trip Planner!\n-----------------------------");
    }

    private void registerUser() {
        System.out.println("Please register before proceeding:");
        currentUser = userService.addUser(User.builder()
                .firstName(promptForInput("First Name: "))
                .lastName(promptForInput("Last Name: "))
                .email(promptForInput("Email: "))
                .password(promptForInput("Password: "))
                .build());
        System.out.println("Registration Successful!\n-----------------------------");
    }

    private String promptForInput(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private void mainMenu() {
        while (true) {
            displayMenuOptions(Arrays.asList(
                    "Choose the type of trip:",
                    "1. Book a package trip",
                    "2. Book a custom trip, choosing individually",
                    "3. Show all bookings",
                    "0. Exit"
            ));

            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> packageMenu();
                case 2 -> customMenu();
                case 3 -> bookingMenu();
                case 0 -> {
                    System.out.println("Thank you for using Trip Planner. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenuOptions(List<String> options) {
        options.forEach(System.out::println);
    }

    private void bookingMenu() {
        bookingService.getAllCustomTripsFromUser(currentUser).forEach(System.out::println);
        bookingService.getAllPackageTripsFromUser(currentUser).forEach(System.out::println);
        mainMenu();
    }

    private void packageMenu() {
        displayMenuOptions(Arrays.asList(
                "Choose a package trip, 1-10:",
                "0 to return to main menu"
        ));
        packageTripsService.getAllPackageTrips();
        handlePackageChoice();
    }

    private void handlePackageChoice() {
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice >= 1 && choice <= 10) {
            bookingService.addToCart(choice, currentUser);
        } else if (choice == 0) {
            mainMenu();
        } else {
            System.out.println("Invalid choice. Please try again.");
            packageMenu();
        }
    }

    private void customMenu() {
        int chosenDestination = addDestinationToCustomTrip();
        int chosenAccommodation = addAccommodationToCustomTrip();
        List<Activity> chosenActivities = addEntitiesToCustomTrip("activity", activityService::getAllActivities, activityService::findById);
        List<Addon> chosenAddons = addEntitiesToCustomTrip("addon", addonService::getAllAddons, addonService::findById);

        double totalPrice = calculateTotalPrice(chosenDestination, chosenAccommodation, chosenActivities, chosenAddons);

        CustomTrip customTrip = CustomTrip.builder()
                .destination(chosenDestination)
                .accommodation(chosenAccommodation)
                .activities(chosenActivities)
                .addons(chosenAddons)
                .totalPrice(totalPrice)
                .build();

        tripService.addTrip(customTrip);
        tripService.addBooking(currentUser, customTrip.getId());
        System.out.println("Trip created successfully!");

        if (Integer.parseInt(promptForInput("0. Go back to the main menu")) != 0) {
            System.out.println("Invalid choice. Please try again.");
            customMenu();
        } else {
            mainMenu();
        }
    }

    private double calculateTotalPrice(int destinationId, int accommodationId, List<Activity> chosenActivities, List<Addon> chosenAddons) {
        double destinationPrice = destinationService.findPriceById(destinationId);
        double accommodationPrice = accomodationService.findPriceById(accommodationId);

        double activityTotal = chosenActivities.stream().mapToDouble(Activity::getPrice).sum();
        double addonTotal = chosenAddons.stream().mapToDouble(Addon::getPrice).sum();

        return destinationPrice + accommodationPrice + activityTotal + addonTotal;
    }

// We'll keep `addDestinationToCustomTrip` and `addAccommodationToCustomTrip` methods as they are.



    private <T> List<T> addEntitiesToCustomTrip(String entityName, Runnable displayAll, java.util.function.Function<Long, T> findById) {
        List<T> entities = new ArrayList<>();
        boolean addMore = true;

        while (addMore) {
            System.out.println("All " + entityName + "s:");
            displayAll.run();

            entities.add(findById.apply(Long.valueOf(promptForInput("Choose a " + entityName + ", input id:"))));

            addMore = "yes".equalsIgnoreCase(promptForInput("Do you want to add more " + entityName + "s? (yes/no):"));
        }

        return entities;
    }

    private int addDestinationToCustomTrip() {
        System.out.println("Choose a destination:");
        destinationService.getAllDestinations();
        return Integer.parseInt(scanner.nextLine());
    }

    private int addAccommodationToCustomTrip() {
        System.out.println("All accommodations:");
        accomodationService.getAllAccomodations();
        return Integer.parseInt(scanner.nextLine());
    }
}

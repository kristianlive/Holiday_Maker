package org.example;

import org.example.entity.User;
import org.example.repository.bookingRepo.BookingRepositoryImp;
import org.example.repository.destinationRepo.DestinationRepositoryImp;
import org.example.repository.packageTripRepo.PackageTripRepositoryImp;
import org.example.repository.userRepo.UserRepositoryImp;
import org.example.services.BookingService;
import org.example.services.DestinationService;
import org.example.services.PackageTripsServices;
import org.example.services.UserService;


import java.util.Scanner;

public class TripPlanner {

    private Scanner scanner;

    public TripPlanner() {
        this.scanner = new Scanner(System.in);
    }

    UserService userService = new UserService(new UserRepositoryImp());
    PackageTripsServices packageTripsService = new PackageTripsServices(new PackageTripRepositoryImp());
    BookingService bookingService = new BookingService(new BookingRepositoryImp());

    DestinationService destinationService = new DestinationService(new DestinationRepositoryImp());

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
        userService.addUser(User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build());
        System.out.println("Registration Successful!");
        System.out.println("-----------------------------");
        userService.getAllUsers();
    }

    private void mainMenu() {
        while (true) {
            System.out.println("Choose the type of trip:");
            System.out.println("1. Package");
            System.out.println("2. Custom");
            System.out.println("3. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    packageMenu();
                    break;
                case 2:
                    customMenu();
                    break;
                case 3:
                    System.out.println("Thank you for using Trip Planner. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
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
        System.out.println("11 to return to main menu");
        // Example packages, you can modify these
        packageTripsService.getAllPackageTrips();

        int choice = Integer.parseInt(scanner.nextLine());

        if (choice >= 1 && choice <= 10) {
            addPackageTripToBooking(choice, user);
        } else if (choice == 11) {
            mainMenu();
        } else {
            System.out.println("Invalid choice. Please try again.");
            packageMenu();
        }
    }

    private void addPackageTripToBooking(int choice, User user) {
        System.out.println("AddPackagetriptobooking method");
        bookingService.addToCart(choice, user);
    }

    private void customMenu() {
        System.out.println("-----------------------------");
        addDestinationToCustomTrip();
        System.out.println("4. Go back to main menu");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:
                mainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                customMenu();
        }
    }

    private void addDestinationToCustomTrip() {
        System.out.println("Choose a destination");
        destinationService.getAllDestinations();
    }

    public static void main(String[] args) {
        TripPlanner tripPlanner = new TripPlanner();
        tripPlanner.run();
    }
}

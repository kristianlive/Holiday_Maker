package org.example;

import org.example.entity.User;
import org.example.repository.packageTripRepo.PackageTripRepositoryImp;
import org.example.repository.userRepo.UserRepositoryImp;
import org.example.services.PackageTripsServices;
import org.example.services.UserService;
import org.example.services.BookingService;
import org.example.repository.bookingRepo.BookingRepositoryImp;



import java.util.Scanner;

public class TripPlanner {

    BookingService bookingService = new BookingService(new BookingRepositoryImp());


    private Scanner scanner;

    public TripPlanner() {
        this.scanner = new Scanner(System.in);
    }
    UserService userService = new UserService(new UserRepositoryImp());
    PackageTripsServices packageTripsService = new PackageTripsServices(new PackageTripRepositoryImp());
    public void run() {
        System.out.println("Welcome to the Trip Planner!");
        System.out.println("-----------------------------");

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
            System.out.println("Choose an option:");
            System.out.println("1. Book Trip");
            System.out.println("2. Change/Display Trip");
            System.out.println("3. Search Booking by LastName");
            System.out.println("4. Remove Booking");
            System.out.println("5. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    packageMenu();
                    break;
                case 2:
                    customMenu();
                    break;
                case 3:
                    System.out.println("Search Booking by LastName");
                    searchBookingByLastName();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void packageMenu() {
        System.out.println("-----------------------------");
        System.out.println("Choose a package trip, 1-10:");
        System.out.println("11 to return to main menu");
        // Example packages, you can modify these
        packageTripsService.getAllPackageTrips();

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1-10:
                // processBeachVacation();
                break;
            case 11:
                mainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                packageMenu();
        }
    }
    private void searchBookingByLastName() {

    }


    private void customMenu() {
        System.out.println("-----------------------------");
        System.out.println("Custom Trip:");
        System.out.println("1. Choose Accommodation");
        System.out.println("2. Choose Add-ons");
        System.out.println("3. Choose Activities");
        System.out.println("4. Go back to main menu");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:

                break;
            case 2:
                // chooseAddons();
                break;
            case 3:
                // chooseActivities();
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                customMenu();
        }
    }

    public static void main(String[] args) {
        TripPlanner tripPlanner = new TripPlanner();
        tripPlanner.run();
    }
}

package org.example;

import org.example.entity.User;
import org.example.repository.bookingRepo.BookingRepositoryImp;
import org.example.repository.packageTripRepo.PackageTripRepositoryImp;
import org.example.repository.userRepo.UserRepositoryImp;
import org.example.services.BookingService;
import org.example.services.PackageTripsServices;
import org.example.services.UserService;

import java.util.List;
import java.util.Scanner;

public class TripPlanner {

    private Scanner scanner;
    private UserService userService;
    private PackageTripsServices packageTripsService;
    private BookingService bookingService;

    public TripPlanner() {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService(new UserRepositoryImp());
        this.packageTripsService = new PackageTripsServices(new PackageTripRepositoryImp());
        this.bookingService = new BookingService(new BookingRepositoryImp());
    }

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
            System.out.println("1. Book Trip");
            System.out.println("2. Change/Display Trip");
            System.out.println("3. Search Booking by LastName");
            System.out.println("4. Remove Booking");
            System.out.println("6. Exit");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    bookTripMenu();
                    break;
                case 2:
                    //changeDisplayTripMenu(); (Se Trello vem har detta uppdrag)
                    break;
                case 3:
                    userService.getAllUsers();
                    searchUsersByLastName();
                    break;
                case 4:
                    //removeBookingMenu(); (Se Trello vem har detta uppdrag)
                    break;
                case 6:
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

        User user = userService.getUser(userId);
        if (user == null) {
            System.out.println("User not found. Please try again.");
            return getUserFromInput();
        }
        System.out.println("Hello " + user.getFirstName());
        return user;
    }

    private void bookTripMenu() {
        System.out.println("Choose the type of trip:");
        System.out.println("1. Package Trip");
        System.out.println("2. Custom Trip");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                packageMenu();
                break;
            case 2:
                customMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                bookTripMenu();
        }
    }
    private void packageMenu() {
        System.out.println("-----------------------------");
        User user = getUserFromInput();
        System.out.println("-----------------------------");
        System.out.println("Choose a package trip, 1-10:");
        System.out.println("11 to return to main menu");

        packageTripsService.getAllPackageTrips();

        int choice = Integer.parseInt(scanner.nextLine());
        if (choice >= 1 && choice <= 10) {
            addPackageTripToBooking(choice, user);
        } else if (choice == 11) {
            return;
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
        System.out.println("Custom Trip:");
        System.out.println("1. Choose Accommodation");
        System.out.println("2. Choose Add-ons");
        System.out.println("3. Choose Activities");
        System.out.println("4. Go back to main menu");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                // chooseAccommodation();
                break;
            case 2:
                // chooseAddons();
                break;
            case 3:
                // chooseActivities();
                break;
            case 4:
                return; // Return to main menu directly
            default:
                System.out.println("Invalid choice. Please try again.");
                customMenu();
        }
    }

    private void searchUsersByLastName() {
        System.out.println("Enter the Last Name to search:");
        String lastName = scanner.nextLine();
        List<User> users = userService.searchUsersByLastName(lastName);
        if (users.isEmpty()) {
            System.out.println("No users found with last name: " + lastName);
        } else {
            for (User user : users) {
                System.out.println(user);
            }
        }
    }




    public static void main(String[] args) {
        TripPlanner tripPlanner = new TripPlanner();
        tripPlanner.run();
    }
}

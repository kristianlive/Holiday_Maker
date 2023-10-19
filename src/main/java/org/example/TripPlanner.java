package org.example;

import org.example.services.AccomodationService;
import java.util.Scanner;

public class TripPlanner {

    private Scanner scanner;

    public TripPlanner() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to the Trip Planner!");
        System.out.println("-----------------------------");

        registerUser();
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
        System.out.println("Registration Successful!");
        System.out.println("-----------------------------");
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

    private void packageMenu() {
        System.out.println("-----------------------------");
        System.out.println("Choose a package trip:");
        // Example packages, you can modify these
        System.out.println("1. Beach Vacation");
        System.out.println("2. Mountain Trekking");
        System.out.println("3. City Tour");
        System.out.println("4. Go back to main menu");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                // processBeachVacation();
                break;
            case 2:
                // processMountainTrekking();
                break;
            case 3:
                // processCityTour();
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                packageMenu();
        }
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

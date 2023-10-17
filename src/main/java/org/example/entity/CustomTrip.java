package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Scanner;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomTrip {
    private int tripId;
    private User userId;
    private Accommodation accommodation;
    private List<Activity> activities;
    private List<Addon> addons;

    public static CustomTrip createCustomTrip(User user, List<Accommodation> accommodationOptions, List<Activity> activityOptions, List<Addon> addonOptions) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available Accommodation Options:");
        displayOptions(accommodationOptions);
        System.out.print("Enter Accommodation ID for your custom trip: ");
        int accommodationId = scanner.nextInt();

        System.out.println("Available Activity Options:");
        displayOptions(activityOptions);
        System.out.print("Enter Activity ID for your custom trip (comma-separated for multiple activities): ");
        String activityIdsInput = scanner.next();
        List<Activity> selectedActivities = parseActivityIds(activityIdsInput, activityOptions);

        System.out.println("Available Addon Options:");
        displayOptions(addonOptions);
        System.out.print("Enter Addon ID for your custom trip (comma-separated for multiple addons): ");
        String addonIdsInput = scanner.next();
        List<Addon> selectedAddons = parseAddonIds(addonIdsInput, addonOptions);

        // Create and return the CustomTrip object
        return CustomTrip.builder()
                .userId(user)
                .accommodation(getAccommodationById(accommodationId, accommodationOptions))
                .activities(selectedActivities)
                .addons(selectedAddons)
                .build();
    }



}


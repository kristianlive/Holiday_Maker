package org.example.entity;

import lombok.*;


import java.util.ArrayList;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Trip {
    private Long id;
    private String name;
    private List<Activity> activities;
    private Accomodation accomodation;
    private List<Addon> addons;
    private double price;



    public void addActivity(Activity activity) {
       if (activities == null) {
           activities = new ArrayList<>();
       }
         activities.add(activity);
       activity.setTrip(this);
    }

}









// CREATE TABLE `holiday-maker`.`trips` (
//  `trip_id` INT NOT NULL AUTO_INCREMENT,
//  `name` VARCHAR(45) NULL,
//  `type` VARCHAR(45) NULL,
//  PRIMARY KEY (`trip_id`));

// CREATE TABLE `holiday-maker`.`bookings` (
//  `bookings_id` INT NOT NULL AUTO_INCREMENT,
//  PRIMARY KEY (`bookings_id`),
//  CONSTRAINT `user_id`
//    FOREIGN KEY (`bookings_id`)
//    REFERENCES `holiday-maker`.`users` (`id`)
//    ON DELETE CASCADE
//    ON UPDATE NO ACTION);
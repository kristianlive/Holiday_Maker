package org.example.entity;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Resa {
    private Long id;
    private String name;
    private List<Activity> activities;
    private Accomodation accomodation;
    private List<Addon> addons;
    private double price;
    private TypAvResa type;
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
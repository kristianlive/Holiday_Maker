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
    private Accomodation accommodation;
    private List<Addon> addons;
    private double price;
    private TypeOfTrip type;



    public void addActivity(Activity activity) {
       if (activities == null) {
           activities = new ArrayList<>();
       }
         activities.add(activity);
       activity.setTrip(this);
    }

}

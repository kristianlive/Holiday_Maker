package org.example.entity;

import lombok.*;


import java.util.ArrayList;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomTrip {
    private Long id;
    private List<Activity> activities;
    private int accommodation;
    private List<Addon> addons;
    private int destination;
    private double totalPrice;



    public void addActivity(Activity activity) {
       if (activities == null) {
           activities = new ArrayList<>();
       }
         activities.add(activity);
       activity.setCustomTrip(this);
    }

}

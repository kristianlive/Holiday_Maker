package org.example.entity;

import lombok.*;


import java.util.ArrayList;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackageTrip {
    private Long id;
    private String destination;
    private String description;
    private int accommodation_id;
    private int addon_id;
    private int activity_id;
    private double price;



}

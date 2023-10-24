package org.example.entity;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@ToString
public class CustomTripDetails {
    private Long tripId;
    private String accommodationType;
    private String city;
    private List<String> activities;
    private List<String> addons;
    private double totalprice;
}

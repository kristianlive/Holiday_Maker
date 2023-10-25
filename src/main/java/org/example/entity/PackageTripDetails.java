package org.example.entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackageTripDetails {
    private Long bookingsId;
    private String description;
    private String addonTitle;
    private String accommodationType;
    private String activityTitle;
    private String destination;
    private double totalprice;
}

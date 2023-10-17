package org.example.entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bookings {
    private Long id;
    private User userId;
    private Trip tripId;
}

package org.example.entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Accomodation {
    private Long id;
    private Resa resaId;
    private String type;
    private String address;
    private double price;
}

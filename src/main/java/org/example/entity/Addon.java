package org.example.entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Addon {
    private Long id;
    private String title;
    private double price;
}

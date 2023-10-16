package org.example.entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
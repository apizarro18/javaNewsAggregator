package com.alexpizarro.javaAggregator.news.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @NotBlank(message = "Username cannot be empty!")
    private String username;

    @Setter
    @Getter
    @NotBlank(message = "Name cannot be left blank!")
    private String firstName;

    @Setter
    @Getter
    @NotBlank(message = "Last name cannot be left blank!")
    private String lastName;

    @Setter
    @Getter
    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Email is required.")
    private String email;

    @Setter
    @Getter
    @NotBlank(message = "Password cannot be empty!")
    private String password;


}

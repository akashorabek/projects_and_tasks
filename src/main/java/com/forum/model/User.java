package com.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    @Column(name = "fullname")
    private String fullName;

    private String password;

    private String role;

    private boolean enabled;

    private String image;

    public User(String email, String fullName, String password, String image) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.image = image;
        enabled = true;
        role = "ROLE_USER";
    }
}

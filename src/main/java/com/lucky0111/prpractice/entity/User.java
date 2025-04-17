package com.lucky0111.prpractice.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String email;

    public User() {}
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}

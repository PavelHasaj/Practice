package com.example.practice;

public class AddUser {
    public String username, email, password, role, image_id;

    public AddUser() {

    }

    public AddUser(String username, String email, String password, String role) {
        //this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        //this.image_id = image_id;
    }
}

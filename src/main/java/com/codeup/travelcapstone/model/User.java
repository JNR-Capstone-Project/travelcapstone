package com.codeup.travelcapstone.model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, unique=true)
    private String username;
    @Column(nullable = false, unique=true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phonenumber;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    private List<Search> post;


    public User() {
    }

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        phonenumber = copy.phonenumber;
    }

    public User(String username, String email, String password, String phonenumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}

package com.codeup.travelcapstone.model;

import javax.persistence.*;

@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String category;
    @Column
    private String action;
    @Column(nullable = false)
    private String date;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Reminder(){

    }

    public Reminder(String category, String action, String date, User user) {
        this.category = category;
        this.action = action;
        this.date = date;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

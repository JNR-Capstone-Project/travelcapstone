package com.codeup.travelcapstone.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "searchs")
public class Search {

        @Id
        @GeneratedValue
        private long id;
        @Column(nullable = false)
        private Date start_date;
        @Column
        private Date end_date;
        @Column(nullable = false)
        private String destination;
        @Column
        private long price;
        @Column
        private boolean non_stop;
        @Column
        private int children;
        @Column(nullable = false)
        private int adults;
        @ManyToOne @JoinColumn(name = "user_id")
        private User user;


    public Search() {
    }


    public Search(Date start_date, Date end_date, String destination, long price,
                  boolean non_stop, int children, int adults, User user) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.destination = destination;
        this.price = price;
        this.non_stop = non_stop;
        this.children = children;
        this.adults = adults;
        this.user = user;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isNon_stop() {
        return non_stop;
    }

    public void setNon_stop(boolean non_stop) {
        this.non_stop = non_stop;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}



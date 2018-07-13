package com.codeup.travelcapstone.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "searches")
public class Search {

        @Id
        @GeneratedValue
        private long id;
        @Column(nullable = false)
        private String start_date;
        @Column
        private String end_date;
        @Column(nullable = false)
        private String destination;
        @Column(nullable = false)
        private String origin;
        @Column(nullable = false)
        private long price;
//        @Column
//        private boolean non_stop;
        @Column(nullable = false)
        private int children;
        @Column(nullable = false)
        private int adults;
        @Column
        private String simpleDateStart;
        @Column
        private String simpleDateEnd;
        @ManyToOne(cascade = CascadeType.MERGE)
        @JoinColumn(name = "user_id")
        private User user;


    public Search() {
    }


    public Search(String start_date, String end_date, String origin, String destination, long price,
                  int children, int adults, String simpleDateStart, String simpleDateEnd, User user) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
//        this.non_stop = non_stop;
        this.children = children;
        this.adults = adults;
        this.simpleDateStart = simpleDateStart;
        this.simpleDateEnd = simpleDateEnd;
        this.user = user;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
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

//    public boolean isNon_stop() {
//        return non_stop;
//    }
//
//    public void setNon_stop(boolean non_stop) {
//        this.non_stop = non_stop;
//    }

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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSimpleDateStart() {
        return simpleDateStart;
    }

    public void setSimpleDateStart(String simpleDateStart) {
        this.simpleDateStart = simpleDateStart;
    }

    public String getSimpleDateEnd() {
        return simpleDateEnd;
    }

    public void setSimpleDateEnd(String simpleDateEnd) {
        this.simpleDateEnd = simpleDateEnd;
    }
}



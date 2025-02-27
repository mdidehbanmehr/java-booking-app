package com.example.demo.room;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private float price;
    private int beds;

    public int getId() {
        return id;
    }

    public Room() {
    }

    public Room( String name, int beds, String description, float price) {
        this.name = name;
        this.beds = beds;
        this.description = description;
        this.price = price;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeds() {
        return beds;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }
}
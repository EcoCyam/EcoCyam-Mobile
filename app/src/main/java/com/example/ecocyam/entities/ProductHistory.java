package com.example.ecocyam.entities;

import java.util.Date;

public class ProductHistory {

    /* default */ private int id;
    /* default */ private String title;
    /* default */ private String marque;
    /* default */ private double rating;
    /* default */ private String dateScan;
    /* default */ private int image;

    public ProductHistory(int id, String title, String marque, double rating, String dateScan, int image) {
        this.id = id;
        this.title = title;
        this.marque = marque;
        this.rating = rating;
        this.dateScan = dateScan;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDateScan() {
        return dateScan;
    }

    public void setDateScan(String dateScan) {
        this.dateScan = dateScan;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

package com.example.ecocyam.entities;

public final class ScannedProduct {
    /* default */private int id,refUser;
    /* default */private String title, marque,localDate;
    /* default */private float rating;

    //voir pour rajouter une image
    public ScannedProduct(int id, int refUser, String tittle, String marque, String localDate, float rating) {
        this.id = id;
        this.refUser = refUser;
        this.title = tittle;
        this.marque = marque;
        this.localDate = localDate;
        this.rating = rating;
    }

    public ScannedProduct(int refUser, String tittle, String marque, String localDate, float rating) {
        this.refUser = refUser;
        this.title = tittle;
        this.marque = marque;
        this.localDate = localDate;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRefUser() {
        return refUser;
    }

    public void setRefUser(int refUser) {
        this.refUser = refUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tittle) {
        this.title = tittle;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

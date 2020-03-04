package com.example.ecocyam.entities;

import java.time.LocalDate;

public final class ScannedProduct {
    /* default */private int id,refUser;
    /* default */private String tittle, marque;
    /* default */private LocalDate localDate;
    /* default */private float rating;

    //voir pour rajouter une image
    public ScannedProduct(int id, int refUser, String tittle, String marque, LocalDate localDate, float rating) {
        this.id = id;
        this.refUser = refUser;
        this.tittle = tittle;
        this.marque = marque;
        this.localDate = localDate;
        this.rating = rating;
    }

    public ScannedProduct(int refUser, String tittle, String marque, LocalDate localDate, float rating) {
        this.refUser = refUser;
        this.tittle = tittle;
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

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

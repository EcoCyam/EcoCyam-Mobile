package com.example.ecocyam.entities;

import android.graphics.Bitmap;

import java.io.Serializable;

public final class ScannedProduct implements Serializable {
    /* default */private int id,refUser;
    /* default */private String title, marque,localDate, serializeImage;
    /* default */private float rating;
    /* default */private Bitmap picture;

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

    public ScannedProduct(int refUser, String tittle, String marque, String localDate, float rating,Bitmap picture) {
        this.refUser = refUser;
        this.title = tittle;
        this.marque = marque;
        this.localDate = localDate;
        this.rating = rating;
        this.picture = picture;
    }

    public ScannedProduct(int id, String title, String marque, String localDate, float rating, int refUser,Bitmap picture) {
        this.id = id;
        this.refUser = refUser;
        this.title = title;
        this.marque = marque;
        this.localDate = localDate;
        this.rating = rating;
        this.picture = picture;
    }

    public ScannedProduct(String title, float rating, int refUser,Bitmap picture) {
        this.refUser = refUser;
        this.title = title;
        this.rating = rating;
        this.picture = picture;
    }

    public ScannedProduct(String title, float rating,Bitmap picture) {
        this.title = title;
        this.rating = rating;
        this.picture = picture;
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

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getSerializeImage() {
        return serializeImage;
    }

    public void setSerializeImage(String serializeImage) {
        this.serializeImage = serializeImage;
    }
}

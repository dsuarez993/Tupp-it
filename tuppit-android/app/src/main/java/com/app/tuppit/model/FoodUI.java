package com.app.tuppit.model;

import android.graphics.drawable.Drawable;

/**
 * Created by David on 25/8/16.
 */
public class FoodUI {
    private String id;
    private String userId;
    private String title;
    private String location;
    private String price;
    private double valoration;
    private int timeleft;
    private boolean followed;

    private String image;

    public FoodUI(String id, String userId, String title, String subtitle, String price, double valoration, int timeleft, boolean followed, String image) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.location = subtitle;
        this.price = price;
        this.valoration = valoration;
        this.timeleft = timeleft;
        this.followed = followed;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String subtitle) {
        this.location = subtitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getValoration() {
        return valoration;
    }

    public void setValoration(double valoration) {
        this.valoration = valoration;
    }

    public int getTimeleft() {
        return timeleft;
    }

    public void setTimeleft(int timeleft) {
        this.timeleft = timeleft;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
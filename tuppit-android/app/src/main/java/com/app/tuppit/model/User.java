package com.app.tuppit.model;

/**
 * Created by David on 4/9/16.
 */
public class User {

    private String id;
    private String token;
    private String name;
    private int profileImage;

    public User(String id, String token, String name, int profileImage) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.profileImage = profileImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }
}

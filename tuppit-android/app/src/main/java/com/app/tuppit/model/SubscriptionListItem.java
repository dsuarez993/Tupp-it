package com.app.tuppit.model;

/**
 * Created by David on 5/9/16.
 */
public class SubscriptionListItem {

    private String id;
    private User user;

    public SubscriptionListItem(String id, User user) {
        this.id = id;
        this.user = user;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.app.tuppit.model;

/**
 * Created by David on 5/9/16.
 */
public class NotificationListItem {

    private String id;
    private String date;
    private String time;
    private String title;
    private String contain;

    public NotificationListItem(String id, String date, String time, String title, String contain) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.title = title;
        this.contain = contain;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContain() {
        return contain;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }
}

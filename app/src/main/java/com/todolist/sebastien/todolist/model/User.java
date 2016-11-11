package com.todolist.sebastien.todolist.model;

/**
 * Created by sebastien on 10/11/16.
 */

public class User {

    String username;
    String urlPhoto;
    long date;

    public User(String username) {
        this.username = username;
    }
    public User(String username, String urlPhoto, long date) {

        this.username = username;
        this.urlPhoto = urlPhoto;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}

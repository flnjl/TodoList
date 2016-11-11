package com.todolist.sebastien.todolist.model;

/**
 * Created by sebastien on 10/11/16.
 */

public class Note {

    private String id;
    private String username;
    private long date;
    private String note;
    private boolean done;

    public Note(String id, String username, long date, String note, boolean done) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.note = note;
        this.done = done;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}

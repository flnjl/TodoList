package com.todolist.sebastien.todolist.session;

/**
 * Created by sebastien on 10/11/16.
 */

public class Session {

    static Session session = null;
    String token = null;

    public static Session getInstance() {
        if (null == session) {
            session = new Session();
        }

        return session;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

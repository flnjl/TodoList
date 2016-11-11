package com.todolist.sebastien.todolist.model;

/**
 * Created by sebastien on 10/11/16.
 */

public class HttpResult {
    public int code;
    public String json;

    public HttpResult(int code, String s) {
        this.code = code;
        this.json = s;
    }
}

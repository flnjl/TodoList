package com.todolist.sebastien.todolist.helper;

import com.todolist.sebastien.todolist.model.Note;
import com.todolist.sebastien.todolist.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sebastien on 10/11/16.
 */

public class JsonParser {
    public static List<Note> getNotes(String json) throws JSONException {
        List<Note> notes = new LinkedList<>();
        JSONArray array = new JSONArray(json);
        JSONObject obj;
        Note note;
        for(int i = 0; i < array.length(); i++){
            obj = array.getJSONObject(i);
            note = new Note(obj.optString("id"), obj.optString("username"), obj.optLong("date"),
                            obj.optString("note"), obj.optBoolean("done"));
            notes.add(note);
        }

        return notes;
    }

    public static String getToken(String response) throws JSONException {
        return new JSONObject(response).optString("token");
    }

    public static List<User> getUsers(String response) throws JSONException {
        JSONArray array = new JSONArray(response);
        List<User> users = new LinkedList<>();
        JSONObject obj;
        User user;
        for(int i=0; i<array.length(); i++){
            obj = array.getJSONObject(i);
            user = new User(obj.optString("username"), obj.optString("urlPhoto"), obj.optLong("date"));
            users.add(user);
        }
        return users;
    }
}

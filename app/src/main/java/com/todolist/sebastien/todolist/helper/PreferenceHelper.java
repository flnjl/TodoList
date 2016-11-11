package com.todolist.sebastien.todolist.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sebastien on 10/11/16.
 */

public class PreferenceHelper {

    private static final String MV_REF = "MesPrefs_DeTodoList";
    private static final String TOKEN_KEY = "token";

    private static final String USERNAME_KEY = "username";

    public static void setToken(final Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MV_REF, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public static String getToken(final Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MV_REF, context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public static void setUsername(final Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MV_REF, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.commit();
    }

    public static String getUsername(final Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MV_REF, context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME_KEY, null);
    }
}

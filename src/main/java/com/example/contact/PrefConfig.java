package com.example.contact;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefConfig {
    private static String LIST_KEY = "list_key";
    public static void writeListInpref(Context context, List<MessageItem> list)
    {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }


    public static List<MessageItem> readListFromPref(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sharedPreferences.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MessageItem>>() {}.getType();
        List<MessageItem> list = gson.fromJson(jsonString, type);
        return list;
    }
}

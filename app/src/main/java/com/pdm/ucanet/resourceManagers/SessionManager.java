package com.pdm.ucanet.resourceManagers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.pdm.ucanet.concreteEntities.User;

/**
 * Created by Crash on 04/06/2017.
 */

public class SessionManager {
    private Gson gson;
    private String json;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context){
        this.context = context;
    }

    public User loadSession(){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
        json = mPrefs.getString("MyObject", "");
        return gson.fromJson(json, User.class);
    }

    public void savingSession(User user){
        //INITIALIZING SHARED PREFERENCES OBJECT (SESSION MANAGER)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPrefs.edit();
        gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("MyObject", json);
        editor.apply();
    }

    public void logOffSession(){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPrefs.edit();
        editor.clear();
        editor.commit();
    }
}

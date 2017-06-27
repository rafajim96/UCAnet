package com.pdm.ucanet.resourceManagers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.pdm.ucanet.HomeActivity;
import com.pdm.ucanet.MainActivity;
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

    private static final String IS_LOGIN = "IsLoggedIn";
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidHivePref";



    public SessionManager(Context context){
        this.context = context;
        mPrefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public User loadSession(){
        //mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
        json = mPrefs.getString("MyObject", "");
        return gson.fromJson(json, User.class);
    }

    public void savingSession(User user){
        //INITIALIZING SHARED PREFERENCES OBJECT (SESSION MANAGER)
        //mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPrefs.edit();
        gson = new Gson();
        String json = gson.toJson(user);
        editor.putBoolean(IS_LOGIN, true);
        editor.putString("MyObject", json);
        editor.apply();
    }

    public boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, MainActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

            return true;
        }
        return false;
    }

    public void logOffSession(){
        //mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mPrefs.edit();
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return mPrefs.getBoolean(IS_LOGIN, false);
    }
}

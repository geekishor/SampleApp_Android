package com.healthcamp.healthapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    Context context;
    SharedPreferences preferences;

    public SharedPreferencesManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(AppText.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value){
        preferences.edit().putString(key, value).apply();
    }

    public void saveBoolean(String key, Boolean value){
        preferences.edit().putBoolean(key, value).apply();
    }

    public void saveInt(String key, int value){
        preferences.edit().putInt(key, value).apply();
    }

    public String retreiveString(String key){
        return preferences.getString(key, "");
    }

    public Boolean retreiveBoolean(String key){
        return preferences.getBoolean(key, false);
    }

    public int retreiveInteger(String key){
        return preferences.getInt(key, 0);
    }

    public void DeletePreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
//        editor.apply();
    }
}

package com.healthcamp.healthapp.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Map;
import java.util.Set;

/**
 * Created by ITH-143 on 3/25/2017.
 */

public class PreferenceHelper implements SharedPreferences {
    public static final String APP_SHARED_PREFS = "healthcamp_prefs";
    public static final String IS_LOGIN = "is_login";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";// token is sent to server
    public static final String GCM_TOKEN = "gcmToken";
    public static final String QUESTION_IMAGE_URL="question_image_url";
    public static final String ANSWER_IMAGE_URL="question_image_url";
    public static final String PROFILE_IMAGE_URL ="profile_image_url";
    public static final String LOGIN_PROFILE_URL ="login_profile_url";
    public static final String LOGIN_PROFILE_IMAGE_NAME = "login_profile_image_name";
    public static final String APP_GCM_REG_DEVICE_ID="register_device_id";
    public static final String POINT = "point";
    public static final String MYRANK = "my_rank";
    SharedPreferences prefs;
    static PreferenceHelper helper;
    public static PreferenceHelper getInstance(Context context){
        if (helper != null){
            return helper;
        }else{
            helper = new PreferenceHelper(context);
            return helper;
        }

    }
    public PreferenceHelper(Context context){
        if (context!=null) {
            prefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        }
    }
    public SharedPreferences getPrefs(){
        return prefs;
    }

    @Override
    public Map<String, ?> getAll() {
        return prefs.getAll();
    }

    @Override
    public String getString(String s, String s2) {
        return prefs.getString(s, s2);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Set<String> getStringSet(String s, Set<String> strings) {
        return prefs.getStringSet(s, strings);
    }

    @Override
    public int getInt(String s, int i) {
        return prefs.getInt(s, i);
    }

    @Override
    public long getLong(String s, long l) {
        return prefs.getLong(s, l);
    }

    @Override
    public float getFloat(String s, float v) {
        return prefs.getFloat(s, v);
    }

    @Override
    public boolean getBoolean(String s, boolean b) {
        return prefs.getBoolean(s, b);
    }

    @Override
    public boolean contains(String s) {
        return prefs.contains(s);
    }

    @Override
    public Editor edit() {
        return prefs.edit();
    }
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }
    public void DeletePreferences(){
        Editor editor = helper.edit();
        editor.clear();
        editor.commit();
        editor.apply();
    }
}

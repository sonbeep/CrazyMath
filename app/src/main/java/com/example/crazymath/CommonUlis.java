package com.example.crazymath;

import android.content.Context;
import android.content.SharedPreferences;

public class CommonUlis {
    private static final String PREF_FILE= "pref_saving";
    private static CommonUlis instance;
    private CommonUlis(){

    }

    public static CommonUlis getInstance() {
        if (instance == null){
            instance = new CommonUlis();
        }
        return instance;
    }
    public void savePref(String key, String value){
        SharedPreferences pref = App.getINSTANCE().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        pref.edit().putString(key, value).apply();
    }
    public String getPref(String key){
        SharedPreferences pref = App.getINSTANCE().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return pref.getString(key, null);
    }
    public void clearPref(String key){
        SharedPreferences pref = App.getINSTANCE().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        pref.edit().remove(key).apply();
    }
}

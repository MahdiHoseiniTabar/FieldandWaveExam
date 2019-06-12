package com.example.fieldandwaveexam;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.security.PublicKey;

public class Mypref {
    public static final String NAME = "name of student";
    public static final String PASS = "PASS of student";




    public static void setName(Context context,String name){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit()
                .putString(NAME,name)
                .apply();
    }
    public static String getName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(NAME,"student name");
    }

    public static void setPass(Context context,String pass){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PASS,pass).apply();
    }
    public static String getPass(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PASS,"student password");
    }
}

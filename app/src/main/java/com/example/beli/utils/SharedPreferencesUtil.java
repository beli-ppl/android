package com.example.beli.utils;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.beli.R;

public class SharedPreferencesUtil {
    public Context mContext;

    public SharedPreferencesUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void writeStringPreferences(String key, String value) {
        SharedPreferences sharedPref = this.mContext.getSharedPreferences(this.mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void writeIntPreferences(String key, Integer value) {
        SharedPreferences sharedPref = this.mContext.getSharedPreferences(this.mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public Integer readIntPreferences(String key) {
        SharedPreferences sharedPref = this.mContext.getSharedPreferences(this.mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int defaultValue = 0;
        int value = sharedPref.getInt(key, defaultValue);

        return value;
    }

    public String readStringPreferences(String key) {
        SharedPreferences sharedPref = this.mContext.getSharedPreferences(this.mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String defaultValue = "";
        String value = sharedPref.getString(key, defaultValue);

        return value;
    }
}

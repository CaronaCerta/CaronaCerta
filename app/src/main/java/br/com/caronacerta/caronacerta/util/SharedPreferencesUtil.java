package br.com.caronacerta.caronacerta.util;


import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String sharedPreferencesKey = "CaronaCerta";

    public static SharedPreferences.Editor getEditor(android.content.Context applicationContext) {
        SharedPreferences pref = applicationContext.getSharedPreferences(sharedPreferencesKey, 0);
        SharedPreferences.Editor editor = pref.edit();
        return editor;
    }

    public static SharedPreferences getPref(android.content.Context applicationContext) {
        SharedPreferences pref = applicationContext.getSharedPreferences(sharedPreferencesKey, 0);
        return pref;
    }
}

package br.com.caronacerta.caronacerta.util;

import android.content.SharedPreferences;

public class SessionUtil {
    public static final String sharedPreferencesKey = "CaronaCerta";

    public static void saveSession(String sessionkey, android.content.Context applicationContext) {
        SharedPreferences pref = applicationContext.getSharedPreferences(sharedPreferencesKey, 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("logged_in", true);
        editor.putString("session_key", sessionkey);

        editor.commit();
    }

    public static boolean isLoggedIn(android.content.Context applicationContext) {
        SharedPreferences pref = applicationContext.getApplicationContext().getSharedPreferences(sharedPreferencesKey, 0);

        return pref.getBoolean("logged_in", false);
    }

    public static String getToken(android.content.Context applicationContext) {
        SharedPreferences pref = applicationContext.getSharedPreferences(sharedPreferencesKey, 0);

        return pref.getString("session_key", "");
    }

    public static void logout(android.content.Context applicationContext) {
        // TODO make a request to /logout
        SharedPreferences pref = applicationContext.getSharedPreferences(sharedPreferencesKey, 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.clear();
        editor.commit();
    }
}

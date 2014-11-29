package br.com.caronacerta.caronacerta.util;

import android.content.SharedPreferences;

public class SessionUtil {
    public static void saveSession(String sessionkey, android.content.Context applicationContext) {
        SharedPreferences.Editor editor = SharedPreferencesUtil.getEditor(applicationContext);

        editor.putBoolean("logged_in", true);
        editor.putString("session_key", sessionkey);

        editor.commit();
    }

    public static boolean isLoggedIn(android.content.Context applicationContext) {
        // TODO try to validate sometimes in the server the session login
        SharedPreferences pref = SharedPreferencesUtil.getPref(applicationContext);

        return pref.getBoolean("logged_in", false);
    }

    public static String getToken(android.content.Context applicationContext) {
        SharedPreferences pref = SharedPreferencesUtil.getPref(applicationContext);

        return pref.getString("session_key", "");
    }

    public static void logout(android.content.Context applicationContext) {
        // TODO make a request to /logout
        SharedPreferences.Editor editor = SharedPreferencesUtil.getEditor(applicationContext);;

        editor.clear();
        editor.commit();
    }
}

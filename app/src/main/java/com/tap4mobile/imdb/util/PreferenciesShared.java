package com.tap4mobile.imdb.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenciesShared {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final String CHAVE_API = "apiKey";
    private final String LANGUAGE = "language";

    public PreferenciesShared(Context contextoParamentro) {
        preferences = contextoParamentro.getSharedPreferences("tap4mobileimdb.preferencias", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    public void saveKey(String apiKey) {
        editor.putString(CHAVE_API, Base64Custom.codificarBase64(apiKey));
        editor.commit();
    }

    public void saveLanguage(String language) {
        editor.putString(LANGUAGE, Base64Custom.codificarBase64(language));
        editor.commit();
    }

    public String getApiKey() {
        return preferences.getString(CHAVE_API, "");
    }

    public String getLanguage() {
        return preferences.getString(LANGUAGE, "");
    }
}
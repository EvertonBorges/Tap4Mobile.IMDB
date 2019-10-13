package com.tap4mobile.imdb.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenciasShared {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final String CHAVE_API = "apiKey";

    public PreferenciasShared(Context contextoParamentro) {
        preferences = contextoParamentro.getSharedPreferences("tap4mobileimdb.preferencias", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    public void salvarDados( String apiKey) {
        editor.putString(CHAVE_API, Base64Custom.codificarBase64(apiKey));
        editor.commit();
    }

    public String getApiKey() {
        return preferences.getString(CHAVE_API, "");
    }
}
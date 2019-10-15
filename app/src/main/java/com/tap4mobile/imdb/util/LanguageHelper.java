package com.tap4mobile.imdb.util;

import android.content.res.Configuration;
import android.content.res.Resources;

import com.tap4mobile.imdb.model.enums.LanguageEnum;

import java.util.Locale;

public class LanguageHelper {

    public static void changeLocale(Resources res, LanguageEnum languageEnum) {
        Configuration config = new Configuration(res.getConfiguration());

        switch (languageEnum) {
            case BRAZIL:
                config.locale = new Locale("pt", "BR");
                break;
            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());

    }

}

package com.tap4mobile.imdb.util;

import android.util.Base64;

public class Base64Custom {

    static String codificarBase64(String texto) {
        String regex = "(\\n|\\r)";
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll(regex, "");
    }

    public static  String decodificarBase64(String textoCodificado) {
        return new String(Base64.decode(textoCodificado.getBytes(), Base64.DEFAULT));
    }

}

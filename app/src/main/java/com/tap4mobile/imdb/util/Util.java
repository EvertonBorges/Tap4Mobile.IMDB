package com.tap4mobile.imdb.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;

import com.tap4mobile.imdb.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Util {

    private static final String BASE_IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    private static final String BASE_MOVIE_PATH = "https://api.themoviedb.org/3/movie";

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String extractYear(String data) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(sdf.parse(data)));

            return String.valueOf(calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String minutesToDuration(Integer minutes){
        int hours = 0;
        while (minutes >= 60) {
            hours++;
            minutes -= 60;
        }
        return hours + " h " + minutes + " min";
    }

    public static String getBaseImagePath() {
        return BASE_IMAGE_PATH;
    }

    public static String getBaseMoviePath() {
        return BASE_MOVIE_PATH;
    }
}

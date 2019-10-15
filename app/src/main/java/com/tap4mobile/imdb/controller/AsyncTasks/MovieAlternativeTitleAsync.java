package com.tap4mobile.imdb.controller.AsyncTasks;

import android.os.AsyncTask;

import com.tap4mobile.imdb.model.IMDB.AlternativeTitle;
import com.tap4mobile.imdb.model.IMDB.MovieDetails;
import com.tap4mobile.imdb.model.IMDB.Title;
import com.tap4mobile.imdb.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class MovieAlternativeTitleAsync extends AsyncTask<String, Void, Title[]> {

    private void configHttpUrlConnection(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-type", "application/json");
        connection.setDoOutput(false);
        connection.setConnectTimeout(5000);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Title[] doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL(Util.getBaseMoviePath() + "/" + params[0] + "/alternative_titles?api_key=" + params[1]);
            connection = (HttpURLConnection) url.openConnection();
            configHttpUrlConnection(connection);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String linha;

            while((linha = reader.readLine()) != null) {
                builder.append(linha);
                builder.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
        }

        AlternativeTitle alternativeTitle = AlternativeTitle.JsonToObject(builder.toString());

        return alternativeTitle.getTitles();
    }

}

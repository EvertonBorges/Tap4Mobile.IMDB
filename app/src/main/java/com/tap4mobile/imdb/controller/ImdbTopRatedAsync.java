package com.tap4mobile.imdb.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.tap4mobile.imdb.model.IMDB.IMDBTopRated;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class ImdbTopRatedAsync extends AsyncTask<String, Void, String> {

    private Context context;

    public ImdbTopRatedAsync(Context context){
        this.context = context;
    }

    private void configHttpUrlConnection(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-type", "application/json");
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = null;
        Scanner scanner = null;

        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/top_rated?api_key=" + params[0] + "&language=" + params[1] + "&page" + params[2]);
            connection = (HttpURLConnection) url.openConnection();
            configHttpUrlConnection(connection);
            connection.connect();

            scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                response.append(scanner.next());
            }
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                connection.disconnect();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String dados) {
        IMDBTopRated topRateds = IMDBTopRated.JsonToObject(dados);


    }

}

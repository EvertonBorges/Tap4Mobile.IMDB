package com.tap4mobile.imdb.controller.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.tap4mobile.imdb.controller.adapters.TopRatedAdapter;
import com.tap4mobile.imdb.model.IMDB.IMDBTopRated;
import com.tap4mobile.imdb.model.IMDB.MovieDetails;
import com.tap4mobile.imdb.model.IMDB.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class MovieDetailsAsync extends AsyncTask<String, Void, MovieDetails> {

    private Context context;

    public MovieDetailsAsync(Context context){
        this.context = context;
    }

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
    protected MovieDetails doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();

        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/" + params[0] + "?api_key=" + params[1] + "&language=" + params[2]);
            connection = (HttpURLConnection) url.openConnection();
            configHttpUrlConnection(connection);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String linha;

            while((linha = reader.readLine()) != null) {
                buffer.append(linha);
                buffer.append("\n");
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


        return MovieDetails.JsonToObject(buffer.toString());
    }

}

package com.tap4mobile.imdb.controller.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tap4mobile.imdb.controller.adapters.TopRatedAdapter;
import com.tap4mobile.imdb.model.IMDB.IMDBTopRated;
import com.tap4mobile.imdb.model.IMDB.Result;
import com.tap4mobile.imdb.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class ImdbTopRatedAsync extends AsyncTask<String, Void, String> {

    private Context context;
    private TopRatedAdapter adapter;
    private List<Result> results;
    private ProgressBar progressBar;

    public ImdbTopRatedAsync(Context context, TopRatedAdapter adapter, List<Result> results, ProgressBar progressBar){
        this.context = context;
        this.adapter = adapter;
        this.results = results;
        this.progressBar = progressBar;
    }

    private void configHttpUrlConnection(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-type", "application/json");
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(Util.getBaseMoviePath() + "/top_rated?api_key=" + params[0] + "&language=" + params[1] + "&page" + params[2]);
            connection = (HttpURLConnection) url.openConnection();
            configHttpUrlConnection(connection);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String linha;
            StringBuilder builder = new StringBuilder();
            while((linha = reader.readLine()) != null) {
                builder.append(linha);
                builder.append("\n");
            }
            return builder.toString();
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
        }
        return null;
    }

    @Override
    protected void onPostExecute(String dados) {
        results.clear();

        IMDBTopRated topRateds = IMDBTopRated.JsonToObject(dados);

        if (topRateds != null) {
            results.addAll(Objects.requireNonNull(topRateds).getResults());

            adapter.notifyDataSetChanged();

            for (Result result : results) {
                new DownloadImageAsync(adapter, result).execute();
            }
        } else {
            Toast.makeText(context, "Por favor verificar conexão com a internet ou API KEY é inválida", Toast.LENGTH_LONG).show();
        }

        progressBar.setVisibility(View.GONE);
    }

}

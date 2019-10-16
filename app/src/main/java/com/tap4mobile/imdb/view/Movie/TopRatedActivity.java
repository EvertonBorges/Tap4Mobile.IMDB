package com.tap4mobile.imdb.view.Movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.tap4mobile.imdb.R;
import com.tap4mobile.imdb.controller.AsyncTasks.ImdbTopRatedAsync;
import com.tap4mobile.imdb.controller.adapters.TopRatedAdapter;
import com.tap4mobile.imdb.model.IMDB.Result;
import com.tap4mobile.imdb.util.Base64Custom;
import com.tap4mobile.imdb.util.PreferenciesShared;

import java.util.ArrayList;
import java.util.List;

public class TopRatedActivity extends AppCompatActivity {

    private List<Result> results = new ArrayList<>();
    private ListView lvMovies;
    private ProgressBar pbLoadMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);

        lvMovies = findViewById(R.id.lvTopRatedMovies);
        pbLoadMovies = findViewById(R.id.pbLoadMovies);

        preconfigs();
    }

    private void preconfigs() {
        TopRatedAdapter adapter = new TopRatedAdapter(TopRatedActivity.this, results);
        lvMovies.setAdapter(adapter);

        PreferenciesShared preferencias = new PreferenciesShared(TopRatedActivity.this);
        new ImdbTopRatedAsync(this, adapter, results, pbLoadMovies).execute(
                Base64Custom.decodificarBase64(preferencias.getApiKey()),
                Base64Custom.decodificarBase64(preferencias.getLanguage()), "1");
    }

}
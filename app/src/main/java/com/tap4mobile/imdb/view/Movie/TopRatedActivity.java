package com.tap4mobile.imdb.view.Movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.tap4mobile.imdb.R;
import com.tap4mobile.imdb.controller.AsyncTasks.ImdbTopRatedAsync;
import com.tap4mobile.imdb.controller.adapters.TopRatedAdapter;
import com.tap4mobile.imdb.model.IMDB.Result;
import com.tap4mobile.imdb.util.Base64Custom;
import com.tap4mobile.imdb.util.PreferenciasShared;

import java.util.ArrayList;
import java.util.List;

public class TopRatedActivity extends AppCompatActivity {

    private List<Result> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);

        ListView rvMovies = findViewById(R.id.rvTopRatedMovies);

        TopRatedAdapter adapter = new TopRatedAdapter(TopRatedActivity.this, results);
        rvMovies.setAdapter(adapter);

        PreferenciasShared preferencias = new PreferenciasShared(TopRatedActivity.this);
        new ImdbTopRatedAsync(TopRatedActivity.this, adapter, results).execute(Base64Custom.decodificarBase64(preferencias.getApiKey()), "pt-BR", "1");
    }
}

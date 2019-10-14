package com.tap4mobile.imdb.view.Movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tap4mobile.imdb.R;
import com.tap4mobile.imdb.model.IMDB.Result;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView ivMoviePoster;
    private TextView tvMovieTitle;
    private TextView tvMovieYear;
    private TextView tvMovieDuration;
    private TextView tvMovieDescription;
    private TextView tvMovieVoteAverage;
    private TextView tvMovieVoteCount;
    private LinearLayout llMovieImages;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        result = (Result) intent.getSerializableExtra("result");

        ivMoviePoster = findViewById(R.id.ivMoviePoster);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieYear = findViewById(R.id.tvMovieYear);
        tvMovieDuration = findViewById(R.id.tvMovieDuration);
        tvMovieDescription = findViewById(R.id.tvMovieDescription);
        tvMovieVoteAverage = findViewById(R.id.tvMovieVoteAverage);
        tvMovieVoteCount = findViewById(R.id.tvMovieVoteCount);
        llMovieImages = findViewById(R.id.llMovieImages);

        preconfiguracoes();
    }

    private void preconfiguracoes() {
        ivMoviePoster.setImageBitmap(result.getPosterBitmap());
        tvMovieTitle.setText(result.getTitle());
        tvMovieYear.setText(result.getYear());
        tvMovieDuration.setText("2 h 4 min");
        tvMovieDescription.setText(result.getOverview());
        tvMovieVoteAverage.setText(String.valueOf(result.getVote_average()));
        tvMovieVoteCount.setText(String.valueOf(result.getVote_count()));
    }

}

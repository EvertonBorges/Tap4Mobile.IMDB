package com.tap4mobile.imdb.view.Movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tap4mobile.imdb.R;
import com.tap4mobile.imdb.controller.AsyncTasks.DownloadImageToImageViewAsync;
import com.tap4mobile.imdb.controller.AsyncTasks.MovieDetailsAsync;
import com.tap4mobile.imdb.model.IMDB.MovieDetails;
import com.tap4mobile.imdb.model.IMDB.Result;
import com.tap4mobile.imdb.util.Base64Custom;
import com.tap4mobile.imdb.util.PreferenciasShared;

import java.util.concurrent.ExecutionException;

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
        Integer movieId = intent.getIntExtra("movieId", 0);

        ivMoviePoster = findViewById(R.id.ivMoviePoster);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieYear = findViewById(R.id.tvMovieYear);
        tvMovieDuration = findViewById(R.id.tvMovieDuration);
        tvMovieDescription = findViewById(R.id.tvMovieDescription);
        tvMovieVoteAverage = findViewById(R.id.tvMovieVoteAverage);
        tvMovieVoteCount = findViewById(R.id.tvMovieVoteCount);
        llMovieImages = findViewById(R.id.llMovieImages);

        preconfiguracoes(movieId);
    }

    private void preconfiguracoes(Integer movieId) {
        PreferenciasShared preferenciasShared = new PreferenciasShared(MovieDetailsActivity.this);

        try {
            MovieDetails movieDetails = new MovieDetailsAsync(this)
                    .execute(String.valueOf(movieId), Base64Custom.decodificarBase64(preferenciasShared.getApiKey()), "pt-BR").get();

            if (movieDetails != null) {
                new DownloadImageToImageViewAsync(movieDetails, ivMoviePoster).execute();
                tvMovieTitle.setText(movieDetails.getOriginal_title());
                tvMovieYear.setText(movieDetails.getYear());
                tvMovieDuration.setText(movieDetails.getDuration());
                tvMovieDescription.setText(movieDetails.getOverview());
                tvMovieVoteAverage.setText(String.valueOf(movieDetails.getVote_average()));
                tvMovieVoteCount.setText(String.valueOf(movieDetails.getVote_count()));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

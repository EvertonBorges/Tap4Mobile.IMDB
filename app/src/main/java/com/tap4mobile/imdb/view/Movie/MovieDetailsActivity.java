package com.tap4mobile.imdb.view.Movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.tap4mobile.imdb.R;
import com.tap4mobile.imdb.controller.AsyncTasks.DownloadImageToImageViewAsync;
import com.tap4mobile.imdb.controller.AsyncTasks.MovieAlternativeTitleAsync;
import com.tap4mobile.imdb.controller.AsyncTasks.MovieDetailsAsync;
import com.tap4mobile.imdb.model.IMDB.MovieDetails;
import com.tap4mobile.imdb.model.IMDB.Title;
import com.tap4mobile.imdb.util.Base64Custom;
import com.tap4mobile.imdb.util.PreferenciesShared;

import java.util.concurrent.ExecutionException;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView ivMoviePoster;
    private TextView tvMovieTitle;
    private TextView tvMovieYear;
    private TextView tvMovieDuration;
    private TextView tvMovieDescription;
    private TextView tvMovieVoteAverage;
    private TextView tvMovieVoteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        Integer movieId = intent.getIntExtra("movieId", 0);

        preconfigs(movieId);
    }

    private void preconfigs(Integer movieId) {
        referenceObjects();

        PreferenciesShared preferenciesShared = new PreferenciesShared(MovieDetailsActivity.this);

        loadDatas(preferenciesShared, movieId);
    }

    private void referenceObjects() {
        ivMoviePoster = findViewById(R.id.ivMoviePoster);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieYear = findViewById(R.id.tvMovieYear);
        tvMovieDuration = findViewById(R.id.tvMovieDuration);
        tvMovieDescription = findViewById(R.id.tvMovieDescription);
        tvMovieVoteAverage = findViewById(R.id.tvMovieVoteAverage);
        tvMovieVoteCount = findViewById(R.id.tvMovieVoteCount);
    }

    private void loadDatas(PreferenciesShared preferenciesShared, Integer movieId) {
        MovieDetails movieDetails = getMovieDetails(preferenciesShared, movieId);

        if (movieDetails != null) {
            loadImage(movieDetails);

            Title title = extractDefinitiveTitle(preferenciesShared, movieDetails);

            tvMovieTitle.setText(title.getTitle());
            tvMovieYear.setText(movieDetails.getYear());
            tvMovieDuration.setText(movieDetails.getDuration());
            tvMovieDescription.setText(movieDetails.getOverview());
            tvMovieVoteAverage.setText(String.valueOf(movieDetails.getVote_average()));
            tvMovieVoteCount.setText(String.valueOf(movieDetails.getVote_count()));
        }
    }

    private MovieDetails getMovieDetails(PreferenciesShared preferenciesShared, Integer movieId) {
        try {
            return new MovieDetailsAsync().execute(
                        String.valueOf(movieId),
                        Base64Custom.decodificarBase64(preferenciesShared.getApiKey()),
                        Base64Custom.decodificarBase64(preferenciesShared.getLanguage()))
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadImage(MovieDetails movieDetails) {
        new DownloadImageToImageViewAsync(movieDetails, ivMoviePoster).execute();
    }

    private Title extractDefinitiveTitle(PreferenciesShared preferenciesShared, MovieDetails movieDetails) {
        try {
            Title[] titles = new MovieAlternativeTitleAsync().execute(String.valueOf(movieDetails.getId()), Base64Custom.decodificarBase64(preferenciesShared.getApiKey())).get();
            Title definitiveTitle = null;
            for (Title title : titles) {
                if (Base64Custom.decodificarBase64(preferenciesShared.getLanguage()).equals("pt-BR") && title.getIso_3166_1().equals("BR")) {
                    definitiveTitle = title;
                }
            }
            if (definitiveTitle == null) {
                definitiveTitle = new Title(movieDetails.getOriginal_language().toUpperCase(), movieDetails.getOriginal_title(), "");
            }
            return definitiveTitle;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}

package com.tap4mobile.imdb.controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.tap4mobile.imdb.R;
import com.tap4mobile.imdb.model.IMDB.Result;
import com.tap4mobile.imdb.view.Movie.MovieDetailsActivity;

import java.util.List;

public class TopRatedAdapter extends ArrayAdapter<Result> {

    private Context context;
    private List<Result> results;

    public TopRatedAdapter(@NonNull Context context, @NonNull List<Result> objects) {
        super(context, 0, objects);
        this.context = context;
        this.results = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (results != null && !results.isEmpty()) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.recycle_top_rated, parent, false);

            ImageView ivPoster = view.findViewById(R.id.ivPoster);
            AppCompatTextView actvMovieTitle = view.findViewById(R.id.actvTitle);
            AppCompatTextView actvMovieYear = view.findViewById(R.id.actvYear);
            AppCompatTextView actvMovieDescription = view.findViewById(R.id.actvDescription);

            Result result = results.get(position);

            if (result.getPosterBitmap() != null) {
                ivPoster.setImageBitmap(result.getPosterBitmap());
            }
            actvMovieTitle.setText(result.getTitle());
            actvMovieYear.setText(result.getYear());
            actvMovieDescription.setText(result.getOverview());

            view.setOnClickListener(v -> {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movieId", result.getId());
                context.startActivity(intent);
            });
        }

        return view;
    }
}

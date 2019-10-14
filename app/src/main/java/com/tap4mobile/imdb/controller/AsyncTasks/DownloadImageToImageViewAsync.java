package com.tap4mobile.imdb.controller.AsyncTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.tap4mobile.imdb.controller.adapters.TopRatedAdapter;
import com.tap4mobile.imdb.model.IMDB.MovieDetails;
import com.tap4mobile.imdb.model.IMDB.Result;

import java.io.InputStream;

public class DownloadImageToImageViewAsync extends AsyncTask<Void, Void, Bitmap> {

    private final MovieDetails movieDetails;
    private final ImageView imageView;

    public DownloadImageToImageViewAsync(MovieDetails movieDetails, ImageView imageView) {
        this.movieDetails = movieDetails;
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(Void... params) {
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(movieDetails.getPoster_path()).openStream();
            image = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        imageView.setImageBitmap(bitmap);
    }
}
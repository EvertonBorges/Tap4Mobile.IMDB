package com.tap4mobile.imdb.controller.AsyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.tap4mobile.imdb.controller.adapters.TopRatedAdapter;
import com.tap4mobile.imdb.model.IMDB.Result;

import java.io.InputStream;

public class DownloadImageAsync extends AsyncTask<Void, Void, Bitmap> {

    private final TopRatedAdapter adapter;
    private final Result result;

    public DownloadImageAsync(TopRatedAdapter adapter, Result result) {
        this.adapter = adapter;
        this.result = result;
    }

    protected Bitmap doInBackground(Void... params) {
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(result.getPoster_path()).openStream();
            image = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        result.setPosterBitmap(bitmap);

        adapter.notifyDataSetChanged();
    }
}
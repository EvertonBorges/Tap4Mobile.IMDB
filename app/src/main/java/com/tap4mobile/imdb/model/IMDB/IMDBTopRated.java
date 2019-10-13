package com.tap4mobile.imdb.model.IMDB;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IMDBTopRated {

    private Integer page;
    private List<Result> results;
    private Integer total_results;
    private Integer total_pages;

    public IMDBTopRated() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public static IMDBTopRated JsonToObject(String json) {
        try {
            JSONObject jsonResult = new JSONObject(json);

            IMDBTopRated topRateds = new IMDBTopRated();

            topRateds.setPage(jsonResult.getInt("page"));
            topRateds.setTotal_results(jsonResult.getInt("total_results"));
            topRateds.setTotal_pages(jsonResult.getInt("total_pages"));

            List<Result> results = new ArrayList<>();
            JSONArray array = new JSONArray(jsonResult.getJSONArray("results").toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                JSONArray genre_ids = jsonObject.getJSONArray("genre_ids");
                Integer[] genre_id = new Integer[genre_ids.length()];
                for (int j = 0; j < genre_ids.length(); j++) {
                    genre_id[j] = genre_ids.getInt(j);
                }

                Result result = new Result();
                result.setPoster_path(jsonObject.getString("poster_path"));
                result.setAdult(jsonObject.getBoolean("adult"));
                result.setOverview(jsonObject.getString("overview"));
                result.setRelease_date(jsonObject.getString("release_date"));
                result.setGenre_ids(genre_id);
                result.setId(jsonObject.getInt("id"));
                result.setOriginal_title(jsonObject.getString("original_title"));
                result.setOriginal_language(jsonObject.getString("original_language"));
                result.setTitle(jsonObject.getString("title"));
                result.setBackdrop_path(jsonObject.getString("backdrop_path"));
                result.setPopularity(jsonObject.getDouble("popularity"));
                result.setVote_count(jsonObject.getInt("vote_count"));
                result.setVideo(jsonObject.getBoolean("video"));
                result.setVote_average(jsonObject.getDouble("vote_average"));

                results.add(result);
            }

            topRateds.setResults(results);

            return topRateds;

        } catch (JSONException e) {
            Log.e("IMDB Tap4Mobile", "Erro no parsing do JSON", e);
            return null;
        }
    }

}
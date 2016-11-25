package com.techcoderz.ruchira.utills;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;

import com.techcoderz.ruchira.R;
import com.techcoderz.ruchira.classtune.model.Genres;
import com.techcoderz.ruchira.classtune.model.NewRelease;
import com.techcoderz.ruchira.classtune.model.ProductionCompanies;
import com.techcoderz.ruchira.classtune.model.ProductionCountries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Shahriar Workspace on 9/6/2016.
 */
public class TaskUtils {

    final static String TAG = "TaskUtils";

    public static boolean isEmpty(String string) {
        if (string == null) {
            return true;
        }
        if (string.isEmpty()) {
            return true;
        }
        if (string.equals("null")) {
            return true;
        }
        return false;
    }


    public static boolean isNotEmpty(String string) {
        return !TaskUtils.isEmpty(string);
    }

    public static List<Genres> setGenur(String json) {
        ArrayList<Genres> orderList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("genres");

            for (int i = 0; i < jsonArray.length(); i++) {
                Genres genres = new Genres();
                genres.setName(jsonArray.getJSONObject(i).getString("name"));
                orderList.add(genres);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public static List<ProductionCountries> setCountries(String json) {
        ArrayList<ProductionCountries> orderList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("production_countries");

            for (int i = 0; i < jsonArray.length(); i++) {
                ProductionCountries genres = new ProductionCountries();
                genres.setName(jsonArray.getJSONObject(i).getString("name"));
                orderList.add(genres);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public static List<ProductionCompanies> setsetCorporation(String json) {
        ArrayList<ProductionCompanies> orderList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("production_companies");

            for (int i = 0; i < jsonArray.length(); i++) {
                ProductionCompanies genres = new ProductionCompanies();
                genres.setName(jsonArray.getJSONObject(i).getString("name"));
                orderList.add(genres);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public static List<NewRelease> setNewRelease(String json) {
        ArrayList<NewRelease> orderList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                NewRelease newRelease = new NewRelease();
                newRelease.setPoster_path(jsonArray.getJSONObject(i).getString("poster_path"));
                newRelease.setAdult(jsonArray.getJSONObject(i).getString("adult"));
                newRelease.setOverview(jsonArray.getJSONObject(i).getString("overview"));
                newRelease.setRelease_date(jsonArray.getJSONObject(i).getString("release_date"));
                newRelease.setId(jsonArray.getJSONObject(i).getString("id"));
                newRelease.setOriginal_title(jsonArray.getJSONObject(i).getString("original_title"));
                newRelease.setOriginal_language(jsonArray.getJSONObject(i).getString("original_language"));
                newRelease.setTitle(jsonArray.getJSONObject(i).getString("title"));
                newRelease.setBackdrop_path(jsonArray.getJSONObject(i).getString("backdrop_path"));
                newRelease.setPopularity(jsonArray.getJSONObject(i).getString("popularity"));
                newRelease.setVote_count(jsonArray.getJSONObject(i).getString("vote_count"));
                newRelease.setVideo(jsonArray.getJSONObject(i).getString("video"));
                newRelease.setVote_average(jsonArray.getJSONObject(i).getString("vote_average"));
                orderList.add(newRelease);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orderList;
    }

}

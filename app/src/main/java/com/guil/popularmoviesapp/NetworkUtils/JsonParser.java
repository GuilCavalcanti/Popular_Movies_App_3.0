package com.guil.popularmoviesapp.NetworkUtils;

import com.guil.popularmoviesapp.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonParser {

    public static JSONObject jsonPopular;
    public static JSONObject jsonTopRated;

    public static void parseJson() {

        try {

            jsonPopular = new JSONObject(MainActivity.movieData.get(0));
            jsonTopRated = new JSONObject(MainActivity.movieData.get(1));

            int jsonLen = jsonPopular.getJSONArray("results").length();

            for (int i = 0; i < 2; i++) {

                if (i == 0) {

                    populateList(MainActivity.popularPosterUrl, jsonPopular, jsonLen);
                } else {

                    populateList(MainActivity.topRatedPosterUrl, jsonTopRated, jsonLen);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void populateList(List<String> list, JSONObject json, int jsonLen) throws JSONException {

       for (int i = 0; i < jsonLen; i++) {

            list.add(json.getJSONArray("results").getJSONObject(i).getString("poster_path"));
        }
    }
}


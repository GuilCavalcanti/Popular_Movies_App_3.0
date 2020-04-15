package com.guil.popularmoviesapp.NetworkUtils;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.guil.popularmoviesapp.MainActivity;
import com.guil.popularmoviesapp.TryAgain;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class JsonParser extends AppCompatActivity {

    public static JSONObject jsonPopular;
    public static JSONObject jsonTopRated;

    public static void parseJson(Context context, Button reloadButton) {

        WeakReference<Context> contextRef = new WeakReference<>(context);

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
        } catch (Exception e) {
            TryAgain tryAgain = new TryAgain();
            tryAgain.displayToast(contextRef.get(), reloadButton);
            e.printStackTrace();
        }
    }

    private static void populateList(List<String> list, JSONObject json, int jsonLen) throws JSONException {

       for (int i = 0; i < jsonLen; i++) {

            list.add(json.getJSONArray("results").getJSONObject(i).getString("poster_path"));
        }
    }
}


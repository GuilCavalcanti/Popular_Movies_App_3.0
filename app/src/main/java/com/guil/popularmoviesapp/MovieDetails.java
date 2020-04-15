package com.guil.popularmoviesapp;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guil.popularmoviesapp.NetworkUtils.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.util.List;

public class MovieDetails {

    private ImageView moviePoster;
    private TextView title;
    private TextView releaseDate;
    private TextView rating;
    private TextView synopsis;
    private WeakReference<Context> contextRef;

    public MovieDetails(Context context, ImageView img, TextView title, TextView releaseDate, TextView rating, TextView synopsis) {

        this.moviePoster = img;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.synopsis = synopsis;
        this.contextRef = new WeakReference<>(context);
    }

    public void buildDetails(List<String> imgUrl, int imgIndex, String sort) {

        JSONObject json;
        String baseUrl = "https://image.tmdb.org/t/p/w500";

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(contextRef.get())
                .load(baseUrl + imgUrl.get(imgIndex))
                .apply(requestOptions)
                .into(moviePoster);

        try {
            if (sort.equals("popular")) {
                json = JsonParser.jsonPopular.getJSONArray("results").getJSONObject(imgIndex);
            } else {
                json = JsonParser.jsonTopRated.getJSONArray("results").getJSONObject(imgIndex);
            }
            title.setText(checkForNull(json.getString("title")));
            releaseDate.setText(parseDate(checkForNull(json.getString("release_date"))));
            rating.setText(checkForNull(json.getString("vote_average")));
            synopsis.setText(checkForNull(json.getString("overview")));
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private String checkForNull(String object) {

        if (object == null || object.equals("")) {
            return "Information Not Available";
        } else {
            return object;
        }
    }

    private String parseDate(String date) {

        String[] splitDate = date.split("-");
        return splitDate[2] + "/" + splitDate[1] + "/" + splitDate[0];
    }
}


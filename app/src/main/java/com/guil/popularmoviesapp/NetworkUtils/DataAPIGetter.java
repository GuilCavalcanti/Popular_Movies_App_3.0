package com.guil.popularmoviesapp.NetworkUtils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Button;
import com.guil.popularmoviesapp.CallAdapter;
import com.guil.popularmoviesapp.MainActivity;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataAPIGetter extends AsyncTask<URL, Void, List<String>> {

    private CallAdapter callAdapter;
    public WeakReference<Context> contextRef;
    public Button reloadButton;

    public DataAPIGetter(CallAdapter callAdapter, Context context, Button reloadButton) {

        this.contextRef = new WeakReference<>(context);
        this.callAdapter = callAdapter;
        this.reloadButton = reloadButton;
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    @Override
    protected List<String> doInBackground(URL... urls) {
        List<String> movieData = new ArrayList<>();

        try {

            movieData.add(getResponseFromHttpUrl(urls[0]));
            movieData.add(getResponseFromHttpUrl(urls[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movieData;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        super.onPostExecute(result);

            MainActivity.movieData = result;
            JsonParser.parseJson(contextRef.get(), reloadButton);
            callAdapter.initRecyclerView(MainActivity.popularPosterUrl);
    }

    public static URL[] apiUrl() {

        String sortCriteria;
        URL[] urls = new URL[2];

        for (int i = 0; i < 2; i++) {

            if (i == 0) {
                sortCriteria = "popular";
            } else {
                sortCriteria = "top_rated";
            }

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(sortCriteria)
                    .appendQueryParameter("api_key", "INSERT_API_KEY_HERE"); // API KEY REQUIRED

            String myUrl = builder.build().toString();

            try {
                URL url = new URL(myUrl);
                urls[i] = url;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return urls;
    }
}

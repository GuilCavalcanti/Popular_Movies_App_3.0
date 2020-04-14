package com.guil.popularmoviesapp.NetworkUtils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.guil.popularmoviesapp.CallAdapter;
import com.guil.popularmoviesapp.MainActivity;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataAPIGetter extends AsyncTask<URL, Void, List<String>> {

    private CallAdapter callAdapter;

    public DataAPIGetter(CallAdapter callAdapter) {

        this.callAdapter = callAdapter;
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
            JsonParser.parseJson();
            callAdapter.initRecyclerView(MainActivity.popularPosterUrl);
            Log.i("Data API", result.toString());
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
                    .appendQueryParameter("api_key", "INSERT API KEY HERE"); // API KEY REQUIRED

            String myUrl = builder.build().toString();

            try {
                URL url = new URL(myUrl);
                Log.i("Url", url.toString());
                urls[i] = url;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return urls;
    }
}

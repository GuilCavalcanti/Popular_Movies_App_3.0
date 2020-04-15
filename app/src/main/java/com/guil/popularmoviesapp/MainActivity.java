package com.guil.popularmoviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.guil.popularmoviesapp.NetworkUtils.DataAPIGetter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<String> movieData;
    public static List<String> popularPosterUrl = new ArrayList<>();
    public static List<String> topRatedPosterUrl = new ArrayList<>();

    public CallAdapter callAdapter;
    public RecyclerView recyclerView;

    public Button reloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reloadButton = findViewById(R.id.reload_button);
        recyclerView = findViewById(R.id.recycler_view);
        callAdapter = new CallAdapter(this, recyclerView);

        DataAPIGetter api = new DataAPIGetter(callAdapter, this, reloadButton);
        api.execute(DataAPIGetter.apiUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sort_by_popularity:
                callAdapter.reloadMovieAdapter(popularPosterUrl, "popular");
                callAdapter.movieAdapter.notifyDataSetChanged();
                return true;

            case R.id.sort_by_rating:
                callAdapter.reloadMovieAdapter(topRatedPosterUrl, "rating");
                callAdapter.movieAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

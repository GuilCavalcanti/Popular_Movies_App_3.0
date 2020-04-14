package com.guil.popularmoviesapp;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.List;

public class CallAdapter {

    private static final int NUM_COLUMNS = 2;

    private View viewPoster;
    private WeakReference<Context> contextRef;

    private MovieDetails movieDetails;

    public CallAdapter(Context context, View view) {

        this.contextRef = new WeakReference<>(context);
        this.viewPoster = view;
    }

    private RecyclerView recyclerView;
    public MovieAdapter movieAdapter;
    public RecyclerView.LayoutManager layoutManager;

    public void initRecyclerView(List<String> imgUrls) {
        recyclerView = viewPoster.findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(contextRef.get(), NUM_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(imgUrls, contextRef.get(), movieDetails);
        recyclerView.setAdapter(movieAdapter);
    }

    public void reloadMovieAdapter(List<String> imgUrls, String sort) {

        movieAdapter.sort = sort;
        movieAdapter.setImagesUrls(imgUrls);
        movieAdapter.notifyDataSetChanged();
    }
}

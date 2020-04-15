package com.guil.popularmoviesapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MoviesDetailsActivity extends AppCompatActivity {

    public ImageView moviePoster;
    public MovieDetails movieDetails;
    public List<String> urls;
    public int indexImg;
    public String sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        TextView title = findViewById(R.id.movie_title);
        TextView releaseDate = findViewById(R.id.movie_release_date);
        TextView rating = findViewById(R.id.movie_vote_average);
        TextView synopsis = findViewById(R.id.movie_synopsis);

        moviePoster = findViewById(R.id.movie_poster_details);
        movieDetails = new MovieDetails(this, moviePoster, title, releaseDate, rating, synopsis);

        Intent intent = getIntent();

        urls = intent.getStringArrayListExtra("imgURLs");
        indexImg = intent.getIntExtra("imgIndex", 55);
        sort = intent.getStringExtra("sort");

        movieDetails.buildDetails(urls, indexImg, sort);
    }
}

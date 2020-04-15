package com.guil.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private List<String> imagesUrls;
    private Context mContext;
    public Intent intent;
    public String sort;

    public MovieAdapter(List<String> mImagesUrls, Context mContext) {
        this.imagesUrls = mImagesUrls;
        this.mContext = mContext;
        this.sort = "popular";
    }

    public void setImagesUrls(List<String> mImagesUrls) {
        this.imagesUrls = mImagesUrls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        String baseUrl = "https://image.tmdb.org/t/p/w500";
        Glide.with(mContext)
                .load(baseUrl + imagesUrls.get(position))
                .apply(requestOptions)
                .into(holder.posterMovie);

        holder.posterMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(v.getContext(), MoviesDetailsActivity.class);
                intent.putStringArrayListExtra("imgURLs", (ArrayList<String>) imagesUrls);
                intent.putExtra("imgIndex", position);
                intent.putExtra("sort", sort);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView posterMovie;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.posterMovie = itemView.findViewById(R.id.movie_poster);
        }
    }
}

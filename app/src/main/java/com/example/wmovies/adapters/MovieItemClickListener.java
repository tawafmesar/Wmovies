package com.example.wmovies.adapters;

import android.widget.ImageView;

import com.example.wmovies.models.Movie;

public interface MovieItemClickListener {

    void onMovieClick(Movie movie, ImageView movieImageView);

}

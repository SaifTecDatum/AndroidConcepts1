package com.myapps.androidconcepts.Interfaces;

public interface MyMviCallbacks {
    void deleteMovies(int id);

    void updateMovies(int id, String movie, String movieDetails, double imdb);
}
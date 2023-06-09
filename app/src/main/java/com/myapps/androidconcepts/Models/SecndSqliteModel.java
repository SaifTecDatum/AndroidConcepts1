package com.myapps.androidconcepts.Models;

public class SecndSqliteModel {
    private int _id;
    private String movie;
    private String movieDetails;
    private double imdb;

    public SecndSqliteModel(int _id, String movie, String movieDetails, double imdb) {
        this._id = _id;
        this.movie = movie;
        this.movieDetails = movieDetails;
        this.imdb = imdb;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(String movieDetails) {
        this.movieDetails = movieDetails;
    }

    public double getImdb() {
        return imdb;
    }

    public void setImdb(double imdb) {
        this.imdb = imdb;
    }
}

package com.furyviewer.service.dto.util;

public class UserStats {
    private int[] series;
    private int[] movies;
    private int[] artists;

    public UserStats(int[] series, int[] movies, int[] artists) {
        this.series = series;
        this.movies = movies;
        this.artists = artists;
    }

    public int[] getSeries() {
        return series;
    }

    public int[] getMovies() {
        return movies;
    }

    public int[] getArtists() {
        return artists;
    }

    public void setSeries(int[] series) {
        this.series = series;
    }

    public void setMovies(int[] movies) {
        this.movies = movies;
    }

    public void setArtists(int[] artists) {
        this.artists = artists;
    }
}

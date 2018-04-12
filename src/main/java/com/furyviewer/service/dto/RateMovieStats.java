package com.furyviewer.service.dto;

import com.furyviewer.domain.Movie;

public class RateMovieStats {

    private Movie movie;
    private double avg;

    public RateMovieStats() {
    }

    public RateMovieStats(Movie movie, double avg) {
        this.movie = movie;
        this.avg = avg;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }
}

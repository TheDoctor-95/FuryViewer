package com.furyviewer.web.rest.vm;



/**
 * View Model Class to Load page watchlist.
 */

public class WatchlistLoadVM{

    private Boolean movies;
    private Integer llista;



    public Boolean isMovies() {
        return movies;
    }

    public WatchlistLoadVM movies(Boolean movies) {
        this.movies = movies;
        return this;
    }

    public void setMovies(Boolean movies) {
        this.movies = movies;
    }

    public Integer getLlista() {
        return llista;
    }

    public WatchlistLoadVM llista(Integer llista) {
        this.llista = llista;
        return this;
    }

    public void setLlista(Integer llista) {
        this.llista = llista;
    }

    @Override
    public String toString() {
        return "WatchlistLoadVM{" +
    
        ", movies='" + isMovies() + "'" +
        ", llista='" + getLlista() + "'" +
        "}";
    }


// jhipster-needle-page-add-getters-setters - Jhipster will add getters and setters here, do not remove

}

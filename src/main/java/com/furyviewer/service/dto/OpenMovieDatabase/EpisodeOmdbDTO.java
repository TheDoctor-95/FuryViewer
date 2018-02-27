
package com.furyviewer.service.dto.OpenMovieDatabase;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion completa de un episode buscada desde OpenMovieDatabase.
 */
public class EpisodeOmdbDTO {
    /**
     * Titulo del episode.
     */
    @SerializedName("Title")
    @Expose
    private String title;

    /**
     * Anyo de la primera emision.
     */
    @SerializedName("Year")
    @Expose
    private String year;

    /**
     * Clasificacion de edades.
     */
    @SerializedName("Rated")
    @Expose
    private String rated;

    /**
     * Fecha de la primera emision.
     */
    @SerializedName("Released")
    @Expose
    private String released;

    /**
     * Numero de la season.
     */
    @SerializedName("Season")
    @Expose
    private String season;

    /**
     * Numero del episode.
     */
    @SerializedName("Episode")
    @Expose
    private String episode;

    /**
     * Duracion del episode.
     */
    @SerializedName("Runtime")
    @Expose
    private String runtime;

    /**
     * Lista con los generos.
     */
    @SerializedName("Genre")
    @Expose
    private String genre;

    /**
     * Lista con los directores.
     */
    @SerializedName("Director")
    @Expose
    private String director;

    /**
     * Lista con los guionistas.
     */
    @SerializedName("Writer")
    @Expose
    private String writer;

    /**
     * Lista con los actores del episode.
     */
    @SerializedName("Actors")
    @Expose
    private String actors;

    /**
     * Sinopsis del episode.
     */
    @SerializedName("Plot")
    @Expose
    private String plot;

    /**
     * Idioma original.
     */
    @SerializedName("Language")
    @Expose
    private String language;

    /**
     * Pais de produccion.
     */
    @SerializedName("Country")
    @Expose
    private String country;

    /**
     * Premios del episode.
     */
    @SerializedName("Awards")
    @Expose
    private String awards;

    /**
     * imagen del episode.
     */
    @SerializedName("Poster")
    @Expose
    private String poster;

    /**
     * Lista con los votos de diferentes webs.
     */
    @SerializedName("Ratings")
    @Expose
    private List<Rating> ratings = null;

    /**
     * Nota en metascore.
     */
    @SerializedName("Metascore")
    @Expose
    private String metascore;

    /**
     * Nota en IMDB.
     */
    @SerializedName("imdbRating")
    @Expose
    private String imdbRating;

    /**
     * Numero de votos en IMDB.
     */
    @SerializedName("imdbVotes")
    @Expose
    private String imdbVotes;

    /**
     * id de IMDB.
     */
    @SerializedName("imdbID")
    @Expose
    private String imdbID;

    /**
     * id de IMDB de la series.
     */
    @SerializedName("seriesID")
    @Expose
    private String seriesID;

    /**
     * Indica que es un episode.
     */
    @SerializedName("Type")
    @Expose
    private String type;

    /**
     * Comprobacion de la llamada a la api.
     */
    @SerializedName("Response")
    @Expose
    private String response;

    /**
     * Devuelve el titulo del episode.
     * @return String | Titulo del episode.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Devuelve el anyo de la primera emision.
     * @return String | Anyo de emision.
     */
    public String getYear() {
        return year;
    }

    /**
     * Devuelve la clasificacion por edades.
     * @return String | Clasificacion por edades.
     */
    public String getRated() {
        return rated;
    }

    /**
     * Devuelve la fecha de la primera emision.
     * @return String | Fecha primera emision.
     */
    public String getReleased() {
        return released;
    }

    /**
     * Devuelve el numero de la season.
     * @return String | Numero de la season.
     */
    public String getSeason() {
        return season;
    }

    /**
     * Devuelve el numero del episode.
     * @return String | Numero del episode.
     */
    public String getEpisode() {
        return episode;
    }

    /**
     * Devuelve la duracion del episode.
     * @return String | Duracion del episode.
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * Devuelve una lista de generos.
     * @return String | Lista de generos.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Devuelve una losta con los directores.
     * @return String | Lista de artist.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Devuelve una lista con los guionistas.
     * @return String | Lista de artist.
     */
    public String getWriter() {
        return writer;
    }

    /**
     * Devuelve una lista con los actores.
     * @return String | Lista de artist.
     */
    public String getActors() {
        return actors;
    }

    /**
     * Devuelve la sinopsis.
     * @return String | Sinopsis.
     */
    public String getPlot() {
        return plot;
    }

    /**
     * Devuelve el idioma original.
     * @return String | Idioma original.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Devuelve el pais de origen.
     * @return String | Pais de origen.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Devuelve los premios del episode.
     * @return String | Premios del episode.
     */
    public String getAwards() {
        return awards;
    }

    /**
     * Devuelve una imagen.
     * @return String | Imagen del episode.
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Devuelve una lista con las votaciones de diferentes webs.
     * @return List | Lista de votaciones.
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * Devuelve la puntuacion de metascore.
     * @return String | Puntuacion de metascore.
     */
    public String getMetascore() {
        return metascore;
    }

    /**
     * Devuelve la puntuacion en IMDB.
     * @return String | Puntuacion de IMDB.
     */
    public String getImdbRating() {
        return imdbRating;
    }

    /**
     * Devuelve el numero de votos de IMDB.
     * @return String | Numero de votos en IMDB.
     */
    public String getImdbVotes() {
        return imdbVotes;
    }

    /**
     * Devuelve el id de IMDB.
     * @return String | id de IMDB.
     */
    public String getImdbID() {
        return imdbID;
    }

    /**
     * Devuelve el id de la series en IMDB.
     * @return String | id de la series en IMDB.
     */
    public String getSeriesID() {
        return seriesID;
    }

    /**
     * Devuelve la comprobacion de que es un episode.
     * @return String | Tipo de la peticion.
     */
    public String getType() {
        return type;
    }

    /**
     * Devuelve una comprobacion de que la peticion ha encontrado resutados.
     * @return String | Comprobacion de la peticion.
     */
    public String getResponse() {
        return response;
    }
}

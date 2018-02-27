
package com.furyviewer.service.dto.OpenMovieDatabase;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de la series buscada desde OpenMovieDatabase.
 */
public class SeriesOmdbDTO {
    /**
     * Titulo de la series.
     */
    @SerializedName("Title")
    @Expose
    private String title;

    /**
     * Anyo entre los que se emitio.
     */
    @SerializedName("Year")
    @Expose
    private String year;

    /**
     * Clasificacion por edades.
     */
    @SerializedName("Rated")
    @Expose
    private String rated;

    /**
     * Fecha de salida.
     */
    @SerializedName("Released")
    @Expose
    private String released;

    /**
     * Duracion media de los episodes.
     */
    @SerializedName("Runtime")
    @Expose
    private String runtime;

    /**
     * Lista con los generos a los que pertence la series.
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
     * Lista con los actores.
     */
    @SerializedName("Actors")
    @Expose
    private String actors;

    /**
     * Sinopsis de la series.
     */
    @SerializedName("Plot")
    @Expose
    private String plot;

    /**
     * Idioma original de la series.
     */
    @SerializedName("Language")
    @Expose
    private String language;

    /**
     * Pais de origen.
     */
    @SerializedName("Country")
    @Expose
    private String country;

    /**
     * Premios recibidos.
     */
    @SerializedName("Awards")
    @Expose
    private String awards;

    /**
     * Url con el poster de la series.
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
     * Nota de metascore.
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
     * Indica que es una series.
     */
    @SerializedName("Type")
    @Expose
    private String type;

    /**
     * Numero total de seasons.
     */
    @SerializedName("totalSeasons")
    @Expose
    private String totalSeasons;

    /**
     * Devuelve una comprobacion si la api encuentra resultados de busqueda o no.
     */
    @SerializedName("Response")
    @Expose
    private String response;

    /**
     * Devuelve el titulo de la series.
     * @return String | Titulo de la series.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Devuelve los anyos entre los que se ha emitido la series.
     * @return String | Anyos de emision.
     */
    public String getYear() {
        return year;
    }

    /**
     * Devuelve la clasificacion de edades.
     * @return String | Clasificacion de edades.
     */
    public String getRated() {
        return rated;
    }

    /**
     * Devuelve la fecha de la primera emision.
     * @return String | Fecha de primera emision.
     */
    public String getReleased() {
        return released;
    }

    /**
     * Devuelve la durcion media de los episodes.
     * @return String | Duracion de los episodes.
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * Devuelve una lista con los generos.
     * @return String | Lista de generos.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Devuelve una lista con los directores.
     * @return String | Lista de dirctores.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Devuelve una lista con los guionistas.
     * @return String | Lista de guionistas.
     */
    public String getWriter() {
        return writer;
    }

    /**
     * Devuelve una lista con los actores.
     * @return String | Lista de actores.
     */
    public String getActors() {
        return actors;
    }

    /**
     * Devuelve la sinopsis de la series.
     * @return String | Sinopsis de la series.
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
     * Devuelve el pais de origen de la series.
     * @return String | Pais de origen.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Devuelve los premios que ha recibido la series.
     * @return String | Premios recibidos.
     */
    public String getAwards() {
        return awards;
    }

    /**
     * Devuelve la url con el poster de la series.
     * @return String | Poster de la series.
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Devuelve una lista con las votaciones de diferentes webs.
     * @return List | Lista de notas.
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * Devuelve la putuacion de metascore.
     * @return String | Puntuacion metascore.
     */
    public String getMetascore() {
        return metascore;
    }

    /**
     * Devuelve la nota de IMDB.
     * @return String | Nota IMDB.
     */
    public String getImdbRating() {
        return imdbRating;
    }

    /**
     * Devuelve el numero de votantes de IMDB.
     * @return String | Numero de votantes.
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
     * Devuelve la comprobacion de que es una series.
     * @return String | Tipo de la peticion.
     */
    public String getType() {
        return type;
    }

    /**
     * Devuelve el numero total de seasons.
     * @return String | Numero total de seasons.
     */
    public String getTotalSeasons() {
        return totalSeasons;
    }

    /**
     * Devuelve la comprobacion de si la peticion se ha realizado correctamente.
     * @return String | Comprobacion de la peticion.
     */
    public String getResponse() {
        return response;
    }
}

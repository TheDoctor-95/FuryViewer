
package com.furyviewer.service.dto.OpenMovieDatabase;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de la movie buscada desde OpenMovieDatabase.
 */
public class MovieOmdbDTO {
    /**
     * Titulo de la movie.
     */
    @SerializedName("Title")
    @Expose
    private String title;

    /**
     * Anyo en el que se emitio.
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
     * Duracion de la movie.
     */
    @SerializedName("Runtime")
    @Expose
    private String runtime;

    /**
     * Lista con los generos a los que pertence la movie.
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
     * Url con el poster de la movie.
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
     * Indica que es una movie.
     */
    @SerializedName("Type")
    @Expose
    private String type;

    /**
     * Fecha de salida del dvd.
     */
    @SerializedName("DVD")
    @Expose
    private String dVD;

    /**
     * Direccion de las oficinas.
     */
    @SerializedName("BoxOffice")
    @Expose
    private String boxOffice;

    /**
     * Productoras de la movie.
     */
    @SerializedName("Production")
    @Expose
    private String production;

    /**
     * Url de la movie.
     */
    @SerializedName("Website")
    @Expose
    private String website;

    /**
     * Comprobacion de si la api ha encontrado datos.
     */
    @SerializedName("Response")
    @Expose
    private String response;

    /**
     * Devuelve el titulo de la movie.
     * @return String | Titulo de la movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Devuelve el anyo de emision de la movie.
     * @return String | Anyo de emision.
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
     * Devuelve la durcion de la movie.
     * @return String | Duracion de la movie.
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
     * Devuelve la sinopsis de la movie.
     * @return String | Sinopsis de la movie.
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
     * Devuelve el pais de origen de la movie.
     * @return String | Pais de origen.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Devuelve los premios que ha recibido la movie.
     * @return String | Premios recibidos.
     */
    public String getAwards() {
        return awards;
    }

    /**
     * Devuelve la url con el poster de la movie.
     * @return String | Poster de la movie.
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
     * Devuelve la comprobacion de que es una movie.
     * @return String | Tipo de la peticion.
     */
    public String getType() {
        return type;
    }

    /**
     * Devuelve la fecha de salida en dvd.
     * @return String | Fecha de salida en dvd.
     */
    public String getDVD() {
        return dVD;
    }

    /**
     * Devuelve la direccion de la productora.
     * @return String | Direccion de la productora.
     */
    public String getBoxOffice() {
        return boxOffice;
    }

    /**
     * Devuelve la productora de la movie.
     * @return String | Nombre de la productora.
     */
    public String getProduction() {
        return production;
    }

    /**
     * Devuelve la url de la web de la movie.
     * @return String | Url de la web.
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Devuelve la comprobacion de si la peticion ha encontrado resultados.
     * @return String | Comprobacion de la peticion.
     */
    public String getResponse() {
        return response;
    }
}

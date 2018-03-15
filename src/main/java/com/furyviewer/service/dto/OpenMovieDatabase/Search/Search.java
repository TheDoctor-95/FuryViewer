
package com.furyviewer.service.dto.OpenMovieDatabase.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Gestiona la informacion de un Search devuelto por OpenMovieDatabase.
 */
public class Search {
    /**
     * Titulo de la movie o series.
     */
    @SerializedName("Title")
    @Expose
    private String title;

    /**
     * Anyo en el que se estreno la movie o series.
     */
    @SerializedName("Year")
    @Expose
    private String year;

    /**
     * id de IMDB.
     */
    @SerializedName("imdbID")
    @Expose
    private String imdbID;

    /**
     * Type del Search ya sea movie o series.
     */
    @SerializedName("Type")
    @Expose
    private String type;

    /**
     * Url con la imagen de la movie o series.
     */
    @SerializedName("Poster")
    @Expose
    private String poster;

    /**
     * Devuelve el titulo de la movie o series.
     * @return String | Titulo.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Devuelve el anyo en el que se estreno.
     * @return String | Anyo de estreno.
     */
    public String getYear() {
        return year;
    }

    /**
     * Devuelve el id de IMDB.
     * @return String | id de IMDB.
     */
    public String getImdbID() {
        return imdbID;
    }

    /**
     * Devuelve el tipo mopvie o series.
     * @return String | Tipo del Search.
     */
    public String getType() {
        return type;
    }

    /**
     * Devuelve una url con el poster de la movie o series.
     * @return String | Poster de la movie o series.
     */
    public String getPoster() {
        return poster;
    }
}

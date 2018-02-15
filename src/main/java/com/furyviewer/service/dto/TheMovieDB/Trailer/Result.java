
package com.furyviewer.service.dto.TheMovieDB.Trailer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de cada trailer por separado.
 */
public class Result {
    /**
     * id interno de la api para reconocer el trailer.
     */
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * iso para reconocer el idioma en el que esta grabado el trailer.
     */
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;

    /**
     * iso para reconocer la region del idioma en el que esta grabado el trailer.
     */
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;

    /**
     * Path del trailer.
     */
    @SerializedName("key")
    @Expose
    private String key;

    /**
     * Titulo del trailer.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Nombre de la plataforma en la que esta el trailer.
     */
    @SerializedName("site")
    @Expose
    private String site;

    /**
     * Resolucion del trailer.
     */
    @SerializedName("size")
    @Expose
    private Integer size;

    /**
     * Tipo del trailer.
     */
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * Devuelve el id del trailer.
     * @return String | id del trailer.
     */
    public String getId() {
        return id;
    }

    /**
     * Devuelve el iso de idioma del trailer.
     * @return String | iso de idioma.
     */
    public String getIso6391() {
        return iso6391;
    }

    /**
     * Devuelve el iso de region del trailer.
     * @return String | iso de region.
     */
    public String getIso31661() {
        return iso31661;
    }

    /**
     * Devuelve el Path del trailer.
     * @return String | Path del trailer.
     */
    public String getKey() {
        return key;
    }

    /**
     * Devuelve el titulo del trailer.
     * @return String | Titulo del trailer.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el host del trailer.
     * @return String | Host del trailer.
     */
    public String getSite() {
        return site;
    }

    /**
     * Devuelve la resolucion del trailer.
     * @return Integer | Resolucion del trailer.
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Devuelve el tipo del trailer.
     * @return String | tipo del trailer.
     */
    public String getType() {
        return type;
    }
}

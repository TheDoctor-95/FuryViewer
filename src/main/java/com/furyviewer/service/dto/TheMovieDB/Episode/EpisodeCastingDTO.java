
package com.furyviewer.service.dto.TheMovieDB.Episode;

import java.util.List;

import com.furyviewer.service.dto.TheMovieDB.Season.Crew;
import com.furyviewer.service.dto.TheMovieDB.Season.GuestStar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de los Artist que participan en un episode buscada desde TheMovieDatabase.
 */
public class EpisodeCastingDTO {

    /**
     * Lista de los actores principales de un episode.
     */
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;

    /**
     * Lista con el director y el guionista de un episode.
     */
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;

    /**
     * Lista con los actores secundarios de un episode.
     */
    @SerializedName("guest_stars")
    @Expose
    private List<GuestStar> guestStars = null;

    /**
     * id interno de la api de TMDB.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Devuelve una lista con todos los actores principales.
     * @return List | Lista de artist.
     */
    public List<Cast> getCast() {
        return cast;
    }

    /**
     * Devuelve una lista con el director y el guionista.
     * @return List | List de artist.
     */
    public List<Crew> getCrew() {
        return crew;
    }

    /**
     * Devuelve una lista con los actores secundarios.
     * @return List | List de artist.
     */
    public List<GuestStar> getGuestStars() {
        return guestStars;
    }

    /**
     * Devuelve el id interno de la api TheMovieDatabase.
     * @return int | id de la api.
     */
    public Integer getId() {
        return id;
    }
}

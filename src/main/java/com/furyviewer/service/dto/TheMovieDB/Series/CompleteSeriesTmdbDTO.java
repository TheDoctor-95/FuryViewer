
package com.furyviewer.service.dto.TheMovieDB.Series;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion completa de la series buscada desde TheMovieDatabase.
 */
public class CompleteSeriesTmdbDTO {
    /**
     * Imagen en horizontal de la series.
     */
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    /**
     * Lista con los creadores de la series.
     */
    @SerializedName("created_by")
    @Expose
    private List<CreatedBy> createdBy = null;

    /**
     * Lista con la duracion mas habitual de los episodios.
     */
    @SerializedName("episode_run_time")
    @Expose
    private List<Integer> episodeRunTime = null;

    /**
     * Fecha de estreno de la series.
     */
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;

    /**
     * Lista de generos de la series.
     */
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;

    /**
     * Pagina web oficial de la sseries.
     */
    @SerializedName("homepage")
    @Expose
    private String homepage;

    /**
     * id interno de la api.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Indica si esta en produccion.
     */
    @SerializedName("in_production")
    @Expose
    private Boolean inProduction;

    /**
     * Idiomas utilizados en la series.
     */
    @SerializedName("languages")
    @Expose
    private List<String> languages = null;

    /**
     * Ultima fecha de emision.
     */
    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;

    /**
     * Titulo de la series.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Lista de las networks de la series.
     */
    @SerializedName("networks")
    @Expose
    private List<Network> networks = null;

    /**
     * Numero de episodios de la series.
     */
    @SerializedName("number_of_episodes")
    @Expose
    private Integer numberOfEpisodes;

    /**
     * Numero de temporadas de la series.
     */
    @SerializedName("number_of_seasons")
    @Expose
    private Integer numberOfSeasons;

    /**
     * Country en la que se rueda la series.
     */
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;

    /**
     * Idioma original de la series.
     */
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    /**
     * Titulo original de la series.
     */
    @SerializedName("original_name")
    @Expose
    private String originalName;

    /**
     * Sinopsis de la series.
     */
    @SerializedName("overview")
    @Expose
    private String overview;

    /**
     * Popularidad de la series en TMDB.
     */
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    /**
     * Path necesario para construir el poster de la series.
     */
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    /**
     * Lista con las productoras de la series.
     */
    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies = null;

    /**
     * Lista con la informacion basica de las seasons.
     */
    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;

    /**
     * Estado de la series.
     */
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * Tipo de la series.
     */
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * Nota de TMDB para la series.
     */
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    /**
     * Votantes de la series.
     */
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    /**
     * Devuelve el path de la imagen horizontal.
     * @return String | Path de la imagen.
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * Devuelve una lista con todos los creadores de la series.
     * @return List | Creadores de la series.
     */
    public List<CreatedBy> getCreatedBy() {
        return createdBy;
    }

    /**
     * Devuelve las duraciones habituales de los episodios.
     * @return List | Duracion de los episodios.
     */
    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    /**
     * Devuelve la primera fecha de emision de la series.
     * @return String | Primera fecha de emision.
     */
    public String getFirstAirDate() {
        return firstAirDate;
    }

    /**
     * Devuelve una lista de los generos de la series.
     * @return List | Generos de la series.
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Devuelve la url oficial de la series.
     * @return String | Url de la series.
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Devuelve el id interno de TheMovieDataBase.
     * @return Integer | id interno de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve si la series esta en produccion o no.
     * @return Boolean | Estado de la serie.
     */
    public Boolean getInProduction() {
        return inProduction;
    }

    /**
     * Devuelve los idiomas utilizados en la series.
     * @return List | Lista de idiomas.
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * Devuelve la ultima fecha de emision.
     * @return String | Ultima fecha de emision.
     */
    public String getLastAirDate() {
        return lastAirDate;
    }

    /**
     * Devuelve el titulo de la series.
     * @return String | Titulo de la series.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve una lista con las networks de las series.
     * @return List | Lista de networks.
     */
    public List<Network> getNetworks() {
        return networks;
    }

    /**
     * Devuelve el numero total de episodios.
     * @return Integer | Numero total de episodios.
     */
    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    /**
     * Devuelve el numero total de seasons.
     * @return Integer | Numero de seasons.
     */
    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    /**
     * Devuelve la country donde se graba la series.
     * @return List | Lista de countrys.
     */
    public List<String> getOriginCountry() {
        return originCountry;
    }

    /**
     * Devuelve el idioma original de la series.
     * @return String | Idioma original.
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * Devuelve el titulo original de la series.
     * @return String | Titulo original.
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Devuelve la sinopsis de la series.
     * @return String | Sinopsis de la series.
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Devuelve la popularidad de la series en la api.
     * @return Double | Popularidad de la series.
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * Devuelve el path necesario para generar el enlace de la imagen.
     * @return String | Path de la imagen.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Devuelve una lista de las productoras de la series.
     * @return List | Lista de productoras.
     */
    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    /**
     * Devuelve una lista con la informacion basica de las seasons.
     * @return List | Lista de seasons de la series.
     */
    public List<Season> getSeasons() {
        return seasons;
    }

    /**
     * Devuelve el estado en el que se encuentra la serie.
     * @return String | Estado de la series.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Devuelve el tipo de la series.
     * @return String | Tipo de la series.
     */
    public String getType() {
        return type;
    }

    /**
     * Devuelve la nota de la api de TheMovieDataBase.
     * @return Double | Nota de la series.
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     * Devuelve el numero de votantes de la series.
     * @return Integer | Numero de votantes.
     */
    public Integer getVoteCount() {
        return voteCount;
    }
}

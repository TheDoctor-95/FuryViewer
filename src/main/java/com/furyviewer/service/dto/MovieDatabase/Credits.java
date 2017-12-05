
package com.furyviewer.service.dto.MovieDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.stream.Collectors;

public class Credits {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }


    public List<Crew> getRelevantCrew(){
        return this.crew
            .stream()
            .filter(c -> c.getJob().toLowerCase().equals("director") || c.getJob().toLowerCase().equals("writing") ||
                c.getJob().toLowerCase().equals("sound") || c.getJob().toLowerCase().equals("screenplay"))
            .collect(Collectors.toList());
    }

}

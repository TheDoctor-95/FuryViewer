package com.furyviewer.service.dto.MovieDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KeywordList {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("keywords")
    @Expose
    private List<com.furyviewer.service.dto.MovieDatabase.Keyword> keywords = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<com.furyviewer.service.dto.MovieDatabase.Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<com.furyviewer.service.dto.MovieDatabase.Keyword> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "KeywordList{" +
            "id=" + id +
            ", keywords=" + keywords +
            '}';
    }
}


package com.furyviewer.service.dto.TheMovieDB.Company;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteCompanyTmdbDTO {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("headquarters")
    @Expose
    private String headquarters;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("logo_path")
    @Expose
    private String logoPath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent_company")
    @Expose
    private ParentCompany parentCompany;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParentCompany getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(ParentCompany parentCompany) {
        this.parentCompany = parentCompany;
    }

}

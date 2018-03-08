package com.furyviewer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserExt.
 */
@Entity
@Table(name = "user_ext")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "location_google_maps")
    private String locationGoogleMaps;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Lob
    @Column(name = "background_img")
    private byte[] background_img;

    @Column(name = "background_img_content_type")
    private String background_imgContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public UserExt image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public UserExt imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getLocationGoogleMaps() {
        return locationGoogleMaps;
    }

    public UserExt locationGoogleMaps(String locationGoogleMaps) {
        this.locationGoogleMaps = locationGoogleMaps;
        return this;
    }

    public void setLocationGoogleMaps(String locationGoogleMaps) {
        this.locationGoogleMaps = locationGoogleMaps;
    }

    public Double getLatitude() {
        return latitude;
    }

    public UserExt latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public UserExt longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public byte[] getBackground_img() {
        return background_img;
    }

    public UserExt background_img(byte[] background_img) {
        this.background_img = background_img;
        return this;
    }

    public void setBackground_img(byte[] background_img) {
        this.background_img = background_img;
    }

    public String getBackground_imgContentType() {
        return background_imgContentType;
    }

    public UserExt background_imgContentType(String background_imgContentType) {
        this.background_imgContentType = background_imgContentType;
        return this;
    }

    public void setBackground_imgContentType(String background_imgContentType) {
        this.background_imgContentType = background_imgContentType;
    }

    public User getUser() {
        return user;
    }

    public UserExt user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExt userExt = (UserExt) o;
        if (userExt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExt{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", locationGoogleMaps='" + getLocationGoogleMaps() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", background_img='" + getBackground_img() + "'" +
            ", background_imgContentType='" + background_imgContentType + "'" +
            "}";
    }
}

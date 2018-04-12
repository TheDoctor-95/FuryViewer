package com.furyviewer.service.dto;

import com.furyviewer.domain.Series;

public class RateSeriesStats {

    private Series serie;
    private Double avg;

    public RateSeriesStats(Series serie, Double avg) {
        this.serie = serie;
        this.avg = avg;
    }

    public RateSeriesStats() {
    }

    public Series getSerie() {
        return serie;
    }

    public void setSerie(Series serie) {
        this.serie = serie;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }
}

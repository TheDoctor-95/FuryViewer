package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.service.dto.OpenMovieDatabase.SeriesOmdbDTO;
import retrofit2.Call;

import java.io.IOException;

public class SeriesOmdbDTOService {

    public static final String apikey = "66f5a28";
    static SeriesOmdbDTORepository apiService = SeriesOmdbDTORepository.retrofit.create(SeriesOmdbDTORepository.class);

    public static SeriesOmdbDTO getSeries(String title) {
        SeriesOmdbDTO series = new SeriesOmdbDTO();
        Call<SeriesOmdbDTO> callSeries = apiService.getSeries(apikey, title);
        try{
            series = callSeries.execute().body();
            System.out.println(series);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return series;
    }
}

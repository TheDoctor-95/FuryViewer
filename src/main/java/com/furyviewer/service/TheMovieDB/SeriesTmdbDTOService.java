package com.furyviewer.service.TheMovieDB;

import com.furyviewer.service.dto.TheMovieDB.Series.CompleteSeriesTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Series.SimpleSeriesTmdbDTO;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
public class SeriesTmdbDTOService {
    private final String apikey = "08526181d206d48ab49b3fa0be7ad1bf";
    private final SeriesTmdbDTORepository apiTMDB = SeriesTmdbDTORepository.retrofit.create(SeriesTmdbDTORepository.class);

    /**
     * Método encargado de devolver el id de la series a partir del nombre de la misma.
     * @param seriesName String | Series a buscar.
     * @return int | id de la serie de la api TMDB.
     */
    public int getIdTmdbSeries(String seriesName) {
        int id = -1;
        SimpleSeriesTmdbDTO series;
        Call<SimpleSeriesTmdbDTO> callSeries = apiTMDB.getSimpleSeries(apikey, seriesName);

        try {
            Response<SimpleSeriesTmdbDTO> response = callSeries.execute();
            if (response.isSuccessful()) {
                series = response.body();
                System.out.println(series);
                id = series.getResults().get(0).getId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * Método encargado de devolver el nombre de la company a partir del de la series.
     * @param seriesName String | Series a buscar.
     * @return String | Nombre de la company.
     */
    public String getCompanyName (String seriesName){
        CompleteSeriesTmdbDTO series;
        int id = getIdTmdbSeries(seriesName);
        String companyName = null;

        if (id != -1) {
            try {
                Call<CompleteSeriesTmdbDTO> callSeries = apiTMDB.getCompleteSeries(id,apikey);

                Response<CompleteSeriesTmdbDTO> response = callSeries.execute();
                if (response.isSuccessful()) {
                    series = response.body();
                    companyName = series.getProductionCompanies().get(0).getName();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return companyName;
    }
}

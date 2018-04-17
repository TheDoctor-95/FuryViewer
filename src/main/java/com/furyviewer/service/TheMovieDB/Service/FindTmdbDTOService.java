package com.furyviewer.service.TheMovieDB.Service;

import com.furyviewer.service.TheMovieDB.Repository.FindTmdbDTORepository;
import com.furyviewer.service.dto.TheMovieDB.find.FindTmdbDTO;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
public class FindTmdbDTOService {
    /**
     * Key proporcionada por la api de TheMovieDB para poder hacer peticiones.
     */
    private final String apikey = "08526181d206d48ab49b3fa0be7ad1bf";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private final FindTmdbDTORepository apiTMDB =
        FindTmdbDTORepository.retrofit.create(FindTmdbDTORepository.class);

    public int getIdTmdbSeriesByImdbId(String imdbId) throws IOException {
        int id = -1;
        FindTmdbDTO find;
        Call<FindTmdbDTO> callArtist = apiTMDB.getFind(imdbId, apikey);

        Response<FindTmdbDTO> response = callArtist.execute();

        if(response.isSuccessful()){
            find = response.body();
            System.out.println(find);
            if (!find.getTvResults().isEmpty()) {
                id = find.getTvResults().get(0).getId();
            }
        }
        else {
            throw new IOException(response.message());
        }

        return id;
    }
}

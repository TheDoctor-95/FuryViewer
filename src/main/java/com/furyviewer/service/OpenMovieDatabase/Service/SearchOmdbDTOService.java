package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.service.OpenMovieDatabase.Repository.SearchOmdbDTORepository;
import com.furyviewer.service.dto.OpenMovieDatabase.Search.Search;
import com.furyviewer.service.dto.OpenMovieDatabase.Search.SearchOmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
public class SearchOmdbDTOService {
    /**
     * Key proporcionada por la api de OpenMovieDataBase para poder hacer peticiones.
     */
    private static final String apikey = "eb62550d";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private static SearchOmdbDTORepository apiOmdb =
        SearchOmdbDTORepository.retrofit.create(SearchOmdbDTORepository.class);

    @Autowired
    private MovieOmdbDTOService movieOmdbDTOService;

    public SearchOmdbDTO getSearchByTitle(String title) {
        SearchOmdbDTO search = null;
        Call<SearchOmdbDTO> callSearch = apiOmdb.getSearch(apikey, title);

        try{
            Response<SearchOmdbDTO> response = callSearch.execute();

            if(response.isSuccessful()){
                search = new SearchOmdbDTO();
                search = response.body();
                System.out.println(search);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return search;
    }

    public void multiImport (String title) {
        SearchOmdbDTO searchOmdbDTO = getSearchByTitle(title);

        if(searchOmdbDTO.getSearch().get(0).getType().equalsIgnoreCase("movie")) {

        }
        for (Search search : searchOmdbDTO.getSearch()) {

        }
    }

}

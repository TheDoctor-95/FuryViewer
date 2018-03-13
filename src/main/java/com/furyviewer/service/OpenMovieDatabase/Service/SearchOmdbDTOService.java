package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.domain.Multimedia;
import com.furyviewer.service.OpenMovieDatabase.Repository.SearchOmdbDTORepository;
import com.furyviewer.service.dto.OpenMovieDatabase.Search.Search;
import com.furyviewer.service.dto.OpenMovieDatabase.Search.SearchOmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

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

    @Autowired
    private SeriesOmdbDTOService seriesOmdbDTOService;

    @Autowired
    AsyncImportTasks asyncImportTasks;

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

    public Multimedia multiImport (String title) {
        SearchOmdbDTO searchOmdbDTO = getSearchByTitle(title);

        Multimedia multimedia = null;

        if(searchOmdbDTO.getSearch().get(0).getType().equalsIgnoreCase("movie")) {
            multimedia = movieOmdbDTOService.importMovieByImdbId(searchOmdbDTO.getSearch().get(0).getImdbID());
        } else if (searchOmdbDTO.getSearch().get(0).getType().equalsIgnoreCase("series")) {
            multimedia = seriesOmdbDTOService.importSeriesByImdbId(searchOmdbDTO.getSearch().get(0).getImdbID());
        }
        List<Search> searches = searchOmdbDTO.getSearch().subList(1, searchOmdbDTO.getSearch().size() - 1);

        asyncImportTasks.importAditionalinBackground(searches);

        return multimedia;
    }

}

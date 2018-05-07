package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.domain.Multimedia;
import com.furyviewer.service.OpenMovieDatabase.Repository.SearchOmdbDTORepository;
import com.furyviewer.service.dto.OpenMovieDatabase.Search.Search;
import com.furyviewer.service.dto.OpenMovieDatabase.Search.SearchOmdbDTO;
import com.furyviewer.service.util.AsyncImportTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * Servicio encargado de gestionar la lista de movies y series que posteriormente se tendrán que importar de forma
 * asincrona.
 * @author IFriedkin
 * @see com.furyviewer.service.OpenMovieDatabase.Repository.SearchOmdbDTORepository
 */
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
    AsyncImportTasksService asyncImportTasksService;

    /**
     * Devuelve una lista de resultados que coinciden con un titulo.
     * @param title String | Titulo de las mopvies o series que se quieren buscar.
     * @return SearchOmdbDTO | Informacion con el formato proporcionado por la API.
     */
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

    /**
     * Importa hasta 10 movies y series que coinciden con el titulo buscado.
     * @param title String | Titulo de las mopvies o series que se quieren buscar.
     * @return Multimedia | Devuelve la primera movie o series encontrada.
     */
    public Multimedia multiImport (String title) {
        SearchOmdbDTO searchOmdbDTO = getSearchByTitle(title);
        List<Search> searches = null;

        Multimedia multimedia = null;

        if(searchOmdbDTO.getResponse().equalsIgnoreCase("true")) {
            if(!searchOmdbDTO.getSearch().isEmpty()) {
                if (searchOmdbDTO.getSearch().get(0).getType().equalsIgnoreCase("movie")) {
                    multimedia = movieOmdbDTOService.importMovieByImdbId(searchOmdbDTO.getSearch().get(0).getImdbID());
                } else if (searchOmdbDTO.getSearch().get(0).getType().equalsIgnoreCase("series")) {
                    multimedia = seriesOmdbDTOService.importSeriesByImdbId(searchOmdbDTO.getSearch().get(0).getImdbID());
                }

                if (searchOmdbDTO.getSearch().size() > 1)
                    searches = searchOmdbDTO.getSearch().subList(1, searchOmdbDTO.getSearch().size());

                if (searches != null) {
                    if (!searches.isEmpty()) {
                        //Se envian el resto de resultados para que se importen de forma asincrona.
                        asyncImportTasksService.importAditionalinBackground(searches);
                    }
                }
            }
        } else {
            System.out.println("==================\nBúsqueda sin resultados\n==================");
        }

        return multimedia;
    }
}

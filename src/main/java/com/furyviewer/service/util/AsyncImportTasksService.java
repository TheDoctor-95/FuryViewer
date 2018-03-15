package com.furyviewer.service.util;

import com.furyviewer.service.OpenMovieDatabase.Service.MovieOmdbDTOService;
import com.furyviewer.service.OpenMovieDatabase.Service.SeriesOmdbDTOService;
import com.furyviewer.service.dto.OpenMovieDatabase.Search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de administrar la importación asincrona de movies y series.
 * @author IFriedkin
 */
@Service
public class AsyncImportTasksService {
    @Autowired
    private MovieOmdbDTOService movieOmdbDTOService;

    @Autowired
    private SeriesOmdbDTOService seriesOmdbDTOService;

    /**
     * Importa todas las movies y series que le llegan en segundo plano.
     * @param searches List | Lista de movies y series a importar.
     */
    @Async
    public void importAditionalinBackground(List<Search> searches) {
        try {
            System.out.println("==================\nEmpieza la importacion asincrona\n==================");

            for (Search search : searches) {
                if(search.getType().equalsIgnoreCase("movie")) {
                    System.out.println("==================\nImportando en asincrono la movie... " + search.getTitle() +
                        "\n==================");

                    movieOmdbDTOService.importMovieByImdbId(search.getImdbID());

                    System.out.println("==================\nImportada movie... " + search.getTitle() +
                        "\n==================");
                } else if (search.getType().equalsIgnoreCase("series")) {
                    System.out.println("==================\nImportando en asincrono la series... " + search.getTitle() +
                        "\n==================");

                    seriesOmdbDTOService.importSeriesByImdbId(search.getImdbID());

                    System.out.println("==================\nImportada series... " + search.getTitle() +
                        "\n==================");
                }

                Thread.sleep(5000L);
            }

            System.out.println("==================\nAcaba la importacion asincrona\n==================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

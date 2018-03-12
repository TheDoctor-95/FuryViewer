package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.service.dto.OpenMovieDatabase.Search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AsyncImportTasks {

    @Autowired
    private MovieOmdbDTOService movieOmdbDTOService;

    @Autowired
    private SeriesOmdbDTOService seriesOmdbDTOService;

    public AsyncImportTasks() {
    }

    @Async
    public void importAditionalinBackground(List<Search> searches) {

        try {
            for (Search search : searches) {
                System.out.println("importing in background " + search.getTitle());

                if(search.getType().equalsIgnoreCase("movie")) {
                    movieOmdbDTOService.importMovieByImdbId(search.getImdbID());
                } else if (search.getType().equalsIgnoreCase("series")) {
                    seriesOmdbDTOService.importSeriesByImdbId(search.getImdbID());
                }
                Thread.sleep(5000L);

            }
        } catch (InterruptedException e) {
        e.printStackTrace();
    }
        /*for (int i = 0; i < 20; i++) {

            System.out.println("importing in background " + title);

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("I'm done!!!! importing in background after 5000 " + title);
        } */


    }
}

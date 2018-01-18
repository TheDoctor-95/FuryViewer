package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.domain.Movie;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.MovieRepository;
import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import retrofit2.Call;

import java.io.IOException;

public class MovieOmdbDTOService {

    public static final String apikey = "66f5a28";


    static MovieOmdbDTORepository apiService = MovieOmdbDTORepository.retrofit.create(MovieOmdbDTORepository.class);


    public static MovieOmdbDTO getMovie(String title) {
        MovieOmdbDTO movie = new MovieOmdbDTO();
        Call<MovieOmdbDTO> callMovie = apiService.getMovie(apikey, title);
        try{
            movie = callMovie.execute().body();
            System.out.println(movie);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return movie;
    }

    public static Movie importMovie(String title){
        MovieOmdbDTO movieOmdbDTO = getMovie(title);

        Movie m = new Movie();

        m.setName(movieOmdbDTO.getTitle());
        m.setDescription(movieOmdbDTO.getPlot());

        //DATES
        m.setReleaseDate(movieOmdbDTO.releseDate());

        //Generos


        try {
            System.out.println(movieOmdbDTO.getPoster());
            m.setImg(toImage(movieOmdbDTO.getPoster()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return m;

    }

    public static byte[] toImage(String url) throws Exception {
        Request request = new Request.Builder()
            .url(url)
            .build();
        OkHttpClient client =new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            return IOUtils.toByteArray(response.body().byteStream());
        }
    }
}

package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.Genre;
import com.furyviewer.domain.Movie;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.GenreRepository;
import com.furyviewer.repository.MovieRepository;
import com.furyviewer.service.ImageService;
import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.Optional;

@Service
public class MovieOmdbDTOService {

    public static final String apikey = "66f5a28";

    @Autowired
    private ImageService imageService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private MovieRepository movieRepository;

    static MovieOmdbDTORepository apiService = MovieOmdbDTORepository.retrofit.create(MovieOmdbDTORepository.class);


    public MovieOmdbDTO getMovie(String title) {
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

    public Movie importMovie(String title){

        Optional<Movie> mdb = movieRepository.findByName(title);
        if(mdb.isPresent()){
            return mdb.get();
        }

        MovieOmdbDTO movieOmdbDTO = getMovie(title);

        Movie m = new Movie();

        m.setName(movieOmdbDTO.getTitle());
        m.setDescription(movieOmdbDTO.getPlot());

        //DATES
        m.setReleaseDate(movieOmdbDTO.releseDate());

        //Generos


        m.setDuration(Double.parseDouble(movieOmdbDTO.getRuntime().split(" ")[0]));

        movieRepository.save(m);

        try {
            System.out.println(movieOmdbDTO.getPoster());
            m.setImg(imageService.toImage(movieOmdbDTO.getPoster()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] genres = movieOmdbDTO.getGenre().split(", ");

        for (String genreStr :
            genres) {
            Optional<Genre> genreOptional = genreRepository.findByName(genreStr);
            Genre genre;
            if (genreOptional.isPresent()){
                genre = genreOptional.get();
            }else{
                genre = new Genre();
                genre.setName(genreStr);

                genre = genreRepository.save(genre);

            }
            m.addGenre(genre);

        }

        movieRepository.save(m);

        String[] actors = movieOmdbDTO.getActors().split(", ");

        for(String actorStr: actors){
            Optional<Artist> optionalActor = artistRepository.findByName(actorStr);
            Artist artist;
            if(optionalActor.isPresent()){
                artist = optionalActor.get();
            }else{
                artist = new Artist();
                artist.setName(actorStr);
                artist = artistRepository.save(artist);
            }

            m.addActorMain(artist);
        }

        movieRepository.save(m);








        return m;

    }


}

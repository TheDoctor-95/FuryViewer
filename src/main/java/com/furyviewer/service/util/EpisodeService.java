package com.furyviewer.service.util;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.ChapterSeen;
import com.furyviewer.domain.Episode;
import com.furyviewer.domain.Season;
import com.furyviewer.domain.Series;
import com.furyviewer.repository.*;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.service.dto.util.CalendarDTO;
import com.furyviewer.service.dto.util.EpisodeSerieDTO;
import com.furyviewer.service.dto.util.EpisodesHomeDTO;
import com.furyviewer.web.rest.EpisodeResource;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio encargado de gestionar la interacción con los episodes.
 * @author IFriedkin
 * @author TheDoctor-95
 */
@Service
public class EpisodeService {
    @Autowired
    private SeriesStatsRepository seriesStatsRepository;

    @Autowired
    private ChapterSeenRepository chapterSeenRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    private final Logger log = LoggerFactory.getLogger(EpisodeResource.class);

    /**
     * Prepara los episodes de una season en el formato de EpisodeHomeDTO para optimizar la query para el frontend.
     * @return List | Informacion optimizada de los episodes.
     * @see com.furyviewer.service.dto.util.EpisodesHomeDTO
     */
    @Timed
    public List<EpisodesHomeDTO> getNextEpisodes(){
        log.debug("getNextEpisodes current time: 1 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());

        List<EpisodesHomeDTO> episodes = new ArrayList<>();
        seriesStatsRepository.followingSeriesUser(SecurityUtils.getCurrentUserLogin()).forEach(
            series -> {
                log.debug("getNextEpisodes current time: 2 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
                Episode nextEpisode = nextEpisode(series);
                EpisodesHomeDTO episodesHomeDTO = new EpisodesHomeDTO();

                log.debug("getNextEpisodes current time: 8 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
                if(nextEpisode!=null &&
                    (nextEpisode.getReleaseDate().isBefore(LocalDate.now()) || nextEpisode.getReleaseDate().isEqual(LocalDate.now()))) {
                    episodesHomeDTO.setEpisodeNumber(nextEpisode.getNumber());
                    episodesHomeDTO.setId(nextEpisode.getSeason().getSeries().getId());
                    episodesHomeDTO.setSeasonNumber(nextEpisode.getSeason().getNumber());
                    episodesHomeDTO.setTitleEpisode(nextEpisode.getName());
                    episodesHomeDTO.setTitleSeries(nextEpisode.getSeason().getSeries().getName());
                    episodesHomeDTO.setUrlCartel(nextEpisode.getSeason().getSeries().getImgUrl());
                    episodesHomeDTO.setReleaseDate(nextEpisode.getReleaseDate());

                    episodes.add(episodesHomeDTO);
                }
                log.debug("getNextEpisodes current time: 9", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
            }
        );

        return episodes;
    }

    /**
     * Mapea el resultado de los siguientes episodes por fecha.
     * @return Map | Episodes ordenados por fecha.
     */
    @Timed
    public Map<LocalDate, List<EpisodesHomeDTO>> getNextEpisodesGroupedByDate(){
        return getNextEpisodes().stream()
            .collect(Collectors.groupingBy(EpisodesHomeDTO::getReleaseDate));
    }

    /**
     * Devuelve la season por la que va el usuario.
     * @param id Long | Id de la series que se quiere saber la season.
     * @return Long | Id de la season actual.
     */
    public Long actualSeason(Long id){
        Series series = seriesRepository.findOne(id);
        Episode e = nextEpisode(series);
        if(e == null){
            //series.getSeasons().;
            List<Long> ids = seasonRepository.findSeasons(id);
            return ids.get(ids.size()-1);
        }else{
            return e.getSeason().getId();
        }
    }

    /**
     * Obtiene el siguiente episode que le toca ver al usuario.
     * @param series Series | Series de la que se quiere averiguar el siguiente episode.
     * @return Episode | SSiguiente episode que tiene que ver el usuario.
     */
    public Episode nextEpisode(Series series){
        Optional<Season> maxSeasonSeenOptional = chapterSeenRepository.findBySeenAndEpisodeSeasonSeriesIdAndUserLogin(true, series.getId(), SecurityUtils.getCurrentUserLogin()).stream()
            .map(chapterSeen ->  chapterSeen.getEpisode().getSeason())
            .max(Comparator.comparing(com.furyviewer.domain.Season::getNumber));

        log.debug("getNextEpisodes current time: 3 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
        if (maxSeasonSeenOptional.isPresent()){
            Season maxSeasonSeen = maxSeasonSeenOptional.get();
            log.debug("getNextEpisodes current time: 4 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
            Episode maxEpisodeSeen = chapterSeenRepository.findBySeenAndEpisodeSeasonSeriesIdAndUserLogin(true, series.getId(), SecurityUtils.getCurrentUserLogin()).stream()
                .map(chapterSeen -> chapterSeen.getEpisode())
                .filter(episode -> episode.getSeason().equals(maxSeasonSeen))
                .max(Comparator.comparing(com.furyviewer.domain.Episode::getNumber)).get();

            if (maxEpisodeSeen.getNumber()<maxSeasonSeen.getEpisodes().size()){
                log.debug("getNextEpisodes current time: 5 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
                return episodeRepository.findByNumberAndSeasonNumberAndSeasonSeriesId(maxEpisodeSeen.getNumber()+1,maxSeasonSeen.getNumber(),series.getId());
            }else{
                log.debug("getNextEpisodes current time: 6 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
                return episodeRepository.findByNumberAndSeasonNumberAndSeasonSeriesId(1,maxSeasonSeen.getNumber()+1,series.getId());
            }
        }else{
            log.debug("getNextEpisodes current time: 7 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
            return episodeRepository.findByNumberAndSeasonNumberAndSeasonSeriesId(1,1,series.getId());
        }
    }

    /**
     * Prepara los episodes de una season en el formato de EpisodeSerieDTO para optimizar la query para el frontend.
     * @param id Long | Id de la season a buscar.
     * @return List | Informacion optimizada de los episodes.
     * @see com.furyviewer.service.dto.util.EpisodeSerieDTO
     */
    public List<EpisodeSerieDTO> chaptersSeriesBySeasonId(Long id){
        List<EpisodeSerieDTO> episodeSerieDTO = new ArrayList<EpisodeSerieDTO>();
        episodeRepository.getEpisodeBySeason(id).forEach(
            episode -> {
                EpisodeSerieDTO esdto = new EpisodeSerieDTO();
                esdto.setId(episode.getId());
                esdto.setDuration(episode.getDuration());
                esdto.setNumber(episode.getNumber());
                esdto.setTitle(episode.getName());
                esdto.setReleaseDate(episode.getReleaseDate());
                esdto.setPlot(episode.getDescription());
                try{
                    esdto.setSeen(chapterSeenRepository.isSeen(SecurityUtils.getCurrentUserLogin(),esdto.getId()));
                }catch (Exception ex){
                    esdto.setSeen(false);
                }

                episodeSerieDTO.add(esdto);
            }
        );
        episodeSerieDTO.stream().sorted();

        return episodeSerieDTO;
    }

    /**
     * Funcion que devuelve todos los episodios que van a salir agrupados i ordenados por fecha.
     * @return SortedMap | Lista de episodes ordenada por fecha.
     */
    public List<CalendarDTO> calendar(){

        List<CalendarDTO> calendarDTOList = new ArrayList<>();

        SortedMap<LocalDate, List<Episode>> map = episodeRepository.
            findBySeasonSeriesIn(
                seriesStatsRepository.followingSeriesUser(SecurityUtils.getCurrentUserLogin()))
            .stream()
            .filter(episode -> episode.getReleaseDate() != null &&
                (   episode.getReleaseDate().isEqual(LocalDate.now())
                 || episode.getReleaseDate().isAfter(LocalDate.now())))
            .collect(Collectors.groupingBy(Episode::getReleaseDate, TreeMap::new, Collectors.toList()));

        for (LocalDate releseDate:
             map.keySet()) {
            List<EpisodesHomeDTO> episodesHomeDTOList = new ArrayList<>();
            for (Episode e:
                 map.get(releseDate)) {
                EpisodesHomeDTO episodesHomeDTO = new EpisodesHomeDTO();
                episodesHomeDTO.setUrlCartel(e.getSeason().getSeries().getImgUrl());
                episodesHomeDTO.setTitleEpisode(e.getName());
                episodesHomeDTO.setSeasonNumber(e.getSeason().getNumber());
                episodesHomeDTO.setId(e.getSeason().getSeries().getId());
                episodesHomeDTO.setEpisodeNumber(e.getNumber());
                episodesHomeDTO.setTitleSeries(e.getSeason().getSeries().getName());

                episodesHomeDTOList.add(episodesHomeDTO);


            }

            CalendarDTO calendarDTO = new CalendarDTO(releseDate, episodesHomeDTOList);
            calendarDTOList.add(calendarDTO);
        }

        return calendarDTOList;
    }

    /**
     * Marca o desmarca todos los episodes de una season como vistos a excepcion de los que todavia no han sido
     * emitidos.
     * @param seasonId Long | Id de la season a marcar como vista.
     * @return Boolean | Devuelve true o false en funcion de si marcamos o desmarcamos los episodes.
     */
    public Boolean seasonSeen(Long seasonId) {
        Boolean state;
        List<Episode> episodes = episodeRepository.getEpisodeBySeason(seasonId);

        int numEpisodesSeen =
            chapterSeenRepository.countEpisodeSeenForSeason(seasonId, SecurityUtils.getCurrentUserLogin());

        if(episodes.size() == numEpisodesSeen) state = false;
        else state = true;

        for(Episode episode : episodes) {
            ChapterSeen chapterSeen = new ChapterSeen();

            Optional<ChapterSeen> auxSeen =
                chapterSeenRepository.findByUserLoginAndEpisodeId(SecurityUtils.getCurrentUserLogin(), episode.getId());

            if (episode.getReleaseDate().isBefore(LocalDate.now()) ||
                episode.getReleaseDate().isEqual(LocalDate.now())) {
                if (auxSeen.isPresent()) chapterSeen.setId(auxSeen.get().getId());

                chapterSeen.setEpisode(episode);
                chapterSeen.setSeen(state);
                chapterSeen.setDate(ZonedDateTime.now());
                chapterSeen.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

                chapterSeenRepository.save(chapterSeen);
            }
        }

        return state;
    }
}

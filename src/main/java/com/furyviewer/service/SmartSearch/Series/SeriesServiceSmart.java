package com.furyviewer.service.SmartSearch.Series;

import com.furyviewer.domain.Series;
import com.furyviewer.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  SeriesServiceSmart crea el resource para poder hacer peticiones desde la api.
 *  @author Whoger
 */
@Service
@Transactional
public class SeriesServiceSmart {

    private final Logger log = LoggerFactory.getLogger(SeriesServiceSmart.class);

    private SeriesRepository seriesRepository;

    public SeriesServiceSmart(SeriesRepository seriesBRepository) {
        this.seriesRepository = seriesRepository;
    }

    /**
     * Guarda la Series en la base de datos.
     * @param series Series | Series que se tiene que guardar.
     * @return Series | Series guardada.
     */
    public Series save(Series series) {
        log.debug("Request to save Series : {}", series);
        return seriesRepository.save(series);
    }

    /**
     * Devuelve todas las Series de la base de datos.
     * @return List<series> | Lista con todas las Series.
     */
    @Transactional(readOnly = true)
    public List<Series> findAll() {
        log.debug("Request to get all SeriesS");
        return seriesRepository.findAll();
    }

    /**
     * Devuelve la información de una Series a partir de su id.
     * @param id Long | id de la Series que se quiere buscar.
     * @return Series | Información de la Series buscada.
     */
    @Transactional(readOnly = true)
    public Series findOne(Long id) {
        log.debug("Request to get Series : {}", id);
        return seriesRepository.findOne(id);
    }

    /**
     * Elimina una Series de la base de datos a partir del id.
     * @param id Long | id de la Series que se quiere eliminar.
     */
    public void delete(Long id) {
        log.debug("Request to delete Series : {}", id);
        seriesRepository.delete(id);
    }
}

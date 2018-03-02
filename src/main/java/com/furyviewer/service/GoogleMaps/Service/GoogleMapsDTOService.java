package com.furyviewer.service.GoogleMaps.Service;

import com.furyviewer.domain.Country;
import com.furyviewer.repository.CountryRepository;
import com.furyviewer.service.GoogleMaps.Repository.GoogleMapsDTORepository;
import com.furyviewer.service.dto.GoogleMaps.AddressComponent;
import com.furyviewer.service.dto.GoogleMaps.GoogleMapsDTO;
import com.furyviewer.service.dto.GoogleMaps.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

/**
 * Servicio encargado de recuperar informacion de una Country desde GoogleMapsDTORepository y la convierte al
 * formato FuryViewer.
 * @author IFriedkin
 * @see com.furyviewer.service.GoogleMaps.Repository.GoogleMapsDTORepository
 */
@Service
public class GoogleMapsDTOService {
    /**
     * Key proporcionada por la api de GoogleMapsGeocoding para poder hacer peticiones.
     */
    private final String apikey = "AIzaSyC69nm386VRBeeAsHR9ipzfleLPcrcP6Xk";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private final GoogleMapsDTORepository apiGoogleMaps =
        GoogleMapsDTORepository.retrofit.create(GoogleMapsDTORepository.class);

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Recupera la informacion de un Country con el formato de la api de GoogleMaps.
     * @param address String | Country a buscar.
     * @return GoogleMapsDTO | Informacion de country en el formato de GoogleMaps
     */
    public GoogleMapsDTO getCoordinates (String address) {
        GoogleMapsDTO maps = null;
        try {

            Call<GoogleMapsDTO> callGoogleMaps = apiGoogleMaps.getCoordinates(address, apikey);

            try {
                maps = callGoogleMaps.execute().body();
                System.out.println(maps);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e);
            maps = null;
        }

        return maps;
    }

    /**
     * Devuelve la longitud de una localizacion.
     * @param countryName String | Localizacion a buscar.
     * @return double | Longitud de la localizacion.
     */
    public double getLongitude (String countryName){
        GoogleMapsDTO maps = getCoordinates(countryName);
        List<Result> resultMap = null;
        double longitude = -1;

        if(maps != null) {
            resultMap = maps.getResults();
            longitude = resultMap.get(0).getGeometry().getLocation().getLng();
        }

        return longitude;
    }

    /**
     * Devuelve la latitud de una localizacion.
     * @param countryName String | Localizacion a buscar.
     * @return double | Latitud de la localizacion.
     */
    public double getLatitude (String countryName){
        GoogleMapsDTO maps = getCoordinates(countryName);
        List<Result> resultMap = null;
        double latitude = -1;

        if(maps != null) {
            resultMap = maps.getResults();
            latitude = resultMap.get(0).getGeometry().getLocation().getLat();
        }

        return latitude;
    }

    /**
     * Devuelve el nombre de una Country.
     * @param countryName String | Localizacion a buscar.
     * @return String | Nombre de la Country filtrado a partir de la api.
     */
    public String getName (String countryName) {
        GoogleMapsDTO maps = getCoordinates(countryName);
        String country = null;
        if(maps != null && !maps.getResults().isEmpty()) {
            List<AddressComponent> nameMaps = maps.getResults().get(0).getAddressComponents();

            for (AddressComponent name : nameMaps) {
                if (name.getTypes().get(0).equalsIgnoreCase("country")) {
                    country = name.getLongName();
                }
            }
        }

        return country;
    }

    /**
     * Se convierte la informacion de Country proveniente de la api de GoogleMaps al formato de FuryViewer.
     * @param countryName String | Nombre de la localizacion.
     * @return Country | Country en el formato FuryViewer.
     */
    public Country importCountry (String countryName){
        Country country = null;

        if(getCoordinates(countryName) != null) {
            country = new Country();
            country.setName(getName(countryName));
            country.setLongitude(getLongitude(getName(countryName)));
            country.setLatitude(getLatitude(getName(countryName)));

            country = countryRepository.save(country);
        }

        return country;
    }
}

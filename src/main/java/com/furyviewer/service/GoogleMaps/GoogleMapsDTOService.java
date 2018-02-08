package com.furyviewer.service.GoogleMaps;

import com.furyviewer.domain.Country;
import com.furyviewer.repository.CountryRepository;
import com.furyviewer.service.dto.GoogleMaps.AddressComponent;
import com.furyviewer.service.dto.GoogleMaps.GoogleMapsDTO;
import com.furyviewer.service.dto.GoogleMaps.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

@Service
public class GoogleMapsDTOService {
    private final String apikey = "AIzaSyC69nm386VRBeeAsHR9ipzfleLPcrcP6Xk";
    private final GoogleMapsDTORepository apiGoogleMaps = GoogleMapsDTORepository.retrofit.create(GoogleMapsDTORepository.class);

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Método encargado de recuperar la información de un Country con el formato de la api de GoogleMaps.
     * @param address String | Country a buscar.
     * @return GoogleMapsDTO | Informafión de country en el formato de GoogleMaps
     */
    public GoogleMapsDTO getCoordinates (String address) {

        GoogleMapsDTO maps = new GoogleMapsDTO();
        Call<GoogleMapsDTO> callGoogleMaps = apiGoogleMaps.getCoordinates(address, apikey);

        try{
            maps = callGoogleMaps.execute().body();
            System.out.println(maps);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return maps;
    }

    /**
     * Método encargado de devolver la longitud de una localización.
     * @param countryName String | Localización a buscar.
     * @return double | Longitud de la localización.
     */
    public double getLongitude (String countryName){
        GoogleMapsDTO maps = getCoordinates(countryName);
        List<Result> resultMap = maps.getResults();

        return resultMap.get(0).getGeometry().getLocation().getLng();
    }

    /**
     * Método encargado de devolver la latitude de una localización.
     * @param countryName String | Localización a buscar.
     * @return double | Latitud de la localización.
     */
    public double getLatitude (String countryName){
        GoogleMapsDTO maps = getCoordinates(countryName);
        List<Result> resultMap = maps.getResults();

        return resultMap.get(0).getGeometry().getLocation().getLat();
    }

    /**
     * Método encargado de devolver el nombre de una Country.
     * @param countryName String | Localización a buscar.
     * @return String | Nombre de la Country filtrado a partr de la api de GoogleMaps.
     */
    public String getName (String countryName) {
        GoogleMapsDTO maps = getCoordinates(countryName);
        String country = null;

        List<AddressComponent> nameMaps = maps.getResults().get(0).getAddressComponents();

        for (AddressComponent name : nameMaps) {
            if(name.getTypes().get(0).equalsIgnoreCase("country")) {
                country = name.getLongName();
            }
        }

        return country;
    }

    /**
     * Se convierte la información de Country proveniente de la api de GoogleMaps al formato de FuryViewer.
     * @param countryName String | Nombre de la localización.
     * @return Country | Country en el formato FuryViewer.
     */
    public Country importCountry (String countryName){
        Country country = new Country();

        country.setName(getName(countryName));
        country.setLongitude(getLongitude(getName(countryName)));
        country.setLatitude(getLatitude(getName(countryName)));

        country = countryRepository.save(country);

        return country;
    }
}

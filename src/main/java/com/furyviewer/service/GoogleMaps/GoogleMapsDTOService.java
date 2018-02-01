package com.furyviewer.service.GoogleMaps;

import com.furyviewer.service.dto.GoogleMaps.GoogleMapsDTO;
import com.furyviewer.service.dto.GoogleMaps.Result;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

@Service
public class GoogleMapsDTOService {
    private final String apikey = "AIzaSyC69nm386VRBeeAsHR9ipzfleLPcrcP6Xk";

    private final GoogleMapsDTORepository apiGoogleMaps = GoogleMapsDTORepository.retrofit.create(GoogleMapsDTORepository.class);

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

    public double getLongitude (String countryName){
        GoogleMapsDTO maps = getCoordinates(countryName);
        List<Result> resultMap = maps.getResults();

        return resultMap.get(0).getGeometry().getLocation().getLng();
    }

    public double getLatitude (String countryName){
        GoogleMapsDTO maps = getCoordinates(countryName);
        List<Result> resultMap = maps.getResults();

        return resultMap.get(0).getGeometry().getLocation().getLat();
    }
}

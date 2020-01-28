package com.here.mwv.service;

import com.here.mwv.configuration.MultiWaypointConfig;
import com.here.mwv.model.GeocodeResponse;
import com.here.mwv.model.Items;
import com.here.mwv.model.Location;
import com.here.mwv.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class GeoCodeService extends  BaseRestService{


    private String geocodeUrl="https://geocode.search.hereapi.com/v1/";

    @Autowired
    private MultiWaypointConfig multiWaypointConfig;

    public Optional<Position> getLatLongByText(Map<String,String> query){

        ResponseEntity<GeocodeResponse> response=this.processRequest("geocode", HttpMethod.GET, GeocodeResponse.class,geocodeUrl,null,this.getHeader(),query);
        GeocodeResponse geocodeResponse=response.getBody();
        if(Objects.nonNull(geocodeResponse)){
          List<Items> items=geocodeResponse.getItems();
          return items.stream().map(Items::getPosition).findFirst();
        }
    return Optional.empty();
    }

    private HttpHeaders getHeader(){
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.populateWithBearer(headers);
        return headers;
    }
}

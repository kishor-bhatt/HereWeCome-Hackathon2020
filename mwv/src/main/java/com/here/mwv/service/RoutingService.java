package com.here.mwv.service;

import com.here.mwv.configuration.MultiWaypointConfig;
import com.here.mwv.model.Position;
import com.here.mwv.model.route.RouteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class RoutingService extends BaseRestService{

    private String routingUrl="https://router.hereapi.com/v1";

    @Autowired
    private MultiWaypointConfig multiWaypointConfig;


    public RouteResponse calculateRouteViaWayPoint(Position origin, Position destination ,Position via, String mode){
        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("origin",this.getLatLong(origin));
        queryMap.put("destination",this.getLatLong(destination));
        queryMap.put("via",this.getLatLong(via));
        queryMap.put("transportMode",mode);
        queryMap.put("return","travelSummary");
        ResponseEntity<RouteResponse> routeResponseResponseEntity=this.processRequest("/routes", HttpMethod.GET,RouteResponse.class,routingUrl,null,this.getHeader(),queryMap);
        return routeResponseResponseEntity.getBody();
    }


    public RouteResponse calculateRoute(Position origin, Position destination , String mode){
        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("origin",this.getLatLong(origin));
        queryMap.put("destination",this.getLatLong(destination));
        queryMap.put("transportMode",mode);
        queryMap.put("return","travelSummary");
        ResponseEntity<RouteResponse> routeResponseResponseEntity=this.processRequest("/routes", HttpMethod.GET,RouteResponse.class,routingUrl,null,this.getHeader(),queryMap);
        return routeResponseResponseEntity.getBody();
    }



    private HttpHeaders getHeader(){
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.populateWithBearer(headers);
        return headers;
    }

    private String getLatLong(Position position){
        return position.getLat()+","+position.getLng();
    }
}

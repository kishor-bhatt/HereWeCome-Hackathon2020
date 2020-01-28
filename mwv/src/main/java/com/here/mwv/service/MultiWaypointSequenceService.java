package com.here.mwv.service;

import com.here.mwv.model.Asset;
import com.here.mwv.model.AssetTimeTable;
import com.here.mwv.model.Assets;
import com.here.mwv.model.Destination;
import com.here.mwv.model.MultiVechileSequencingRequest;
import com.here.mwv.model.MultiWayPointSequenceResponse;
import com.here.mwv.model.Position;
import com.here.mwv.model.Vehicle;
import com.here.mwv.model.Vechiles;
import com.here.mwv.model.response.PositionReference;
import com.here.mwv.model.response.Route;
import com.here.mwv.model.route.RouteResponse;
import com.here.mwv.model.route.Section;
import com.here.mwv.model.route.Sections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class MultiWaypointSequenceService {

    @Autowired
    private GeoCodeService geoCodeService;

    @Autowired
    private RoutingService routingService;

    private Map<String,List<Double>> distanceMatrix;

    private Map<String,List<Asset>> vechileAssetMap;



    public MultiWayPointSequenceResponse getMultiWayPointSeqenceResponsne(MultiVechileSequencingRequest multiVechileSequencingRequest){

        String finalDestination=multiVechileSequencingRequest.getDestination();
        Position finalPos=null;
        Map<Vehicle,Integer> vehcileTimeTable=new HashMap <>();

        //Getting final Destination
        Map<String,String> queryMap= new HashMap <>();
        queryMap.put("q",finalDestination);
        Optional<Position> optionalPosition=geoCodeService.getLatLongByText(queryMap);
        if(optionalPosition.isPresent()){
            finalPos=optionalPosition.get();
        }
        log.info("Location Info",finalPos);

        Vechiles vechiles=multiVechileSequencingRequest.getVechiles();
        Assets assets =multiVechileSequencingRequest.getAssets();
        int fleetCount=vechiles.getVehicle().size();
       /* this.distanceMatrix= new HashMap <>(fleetCount);
        //Build The distance Matrix
        this.buildDistanceMatrix(vechiles.getVehicle(),assets.getAsset());

        // Get The first level Vehicle Asset Map
        final Position destination=finalPos;

        Map<String,List<Asset>> vechileAssetMap =this.get1stLevelVechileMap(assets.getAsset());
        vechileAssetMap.forEach((k,v)->{
            Vehicle vehicle =this.getVechileDetailsById(k,vechiles);
            routingService.calculateRoute(this.getLatLongFromVehicle(vehicle),destination,this.getLatLongFromAsset(v.get(0)),vechiles.getType());

        });*/

        //Time Taken each vehicle to reach the final destination .
        Map<Vehicle, Integer>  timeTakenToReachDestinationMap=this.getStartToFinalDestination(vechiles,finalPos);

        //One Stop Map
        Map<Vehicle, List<AssetTimeTable>> oneStopTimeTableMap=this.getVehichleAssetTimeTableMap(timeTakenToReachDestinationMap,assets);


       //Minimun Time Table Map

        Map<Vehicle, List<AssetTimeTable>> vehicleMap= this.getFirstMinimumTimeTableMap(oneStopTimeTableMap);




        // Get the 2nd Order


        vehicleMap.forEach((k,v)->{
            if(oneStopTimeTableMap.containsKey(k)){
                List<AssetTimeTable>  assetTimeTables= oneStopTimeTableMap.get(k);
                AssetTimeTable selectedPickUp=v.get(0);
                String assetId=selectedPickUp.getAsset().getAssetId();
                assetTimeTables.parallelStream().forEach(assetTimeTable -> {
                    if(assetTimeTable.getAsset().getAssetId().equalsIgnoreCase(assetId))
                        assetTimeTable.setTime(0);
                });
            }
        });
       this.getAssetAccomodationListByCapacity(vehicleMap,oneStopTimeTableMap);
       List<com.here.mwv.model.response.Vehicle> vehicles= new ArrayList <>();
      //  List<Route> routes= new ArrayList <>();
        vehicleMap.forEach((k,v)->{
            // Individual Vehicle
            Map<String, PositionReference> routeMap=new HashMap <>();
            int i=1;
            for (AssetTimeTable route : v) {
                int j = i + 1;
                //route.getAsset().getLattitude()
                PositionReference positionReference = PositionReference.builder().lat(route.getAsset().getLattitude())
                        .lng(route.getAsset().getLongitude())
                        .ref(Integer.toString(j))
                        .build();
                routeMap.put(String.valueOf(i), positionReference);
                i++;
            }
         //   routes.add(Route.builder().positionReferenceMap(routeMap).build());
            vehicles.add(com.here.mwv.model.response.Vehicle.builder().id(k.getId()).number(k.getId()).routeList(Route.builder().positionReferenceMap(routeMap).build()).build());
        });
        return MultiWayPointSequenceResponse.builder().vehicle(vehicles).build();
        /*Asset asset=new Asset();
        asset.setAssetId("123");
        asset.setLattitude(10.090908);
        asset.setLongitude(12.056578);

        Destination destination =new Destination();
        destination.setAssets(asset);

        List<Destination> destinationList=new ArrayList<>();
        destinationList.add(destination);
        return MultiWayPointSequenceResponse.builder()
                .destination(destinationList)
                .pickupTime("28/01/2020 - 7:00:00 +0000")
                .totalpickUps(2)
                .vechileId("HERE-VEC-0001")
                .build();*/

      //return MultiWayPointSequenceResponse.builder().build();
    }
    private  double getDistance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

    private Map<String,List<Double>> getDistanceMatrix(int size){

        if(Objects.isNull(distanceMatrix))
            return new HashMap <>();
        return distanceMatrix;
    }

    private void buildDistanceMatrix(List<Vehicle> vehicles, List<Asset> assetList){


        vehicles.forEach(vehicle -> {
            //
            if(!this.distanceMatrix.containsKey(vehicle.getId())){
                List<Double> distanceList=new ArrayList <>();
                assetList.forEach( asset -> {
                    double distance = this.getDistance(vehicle.getLattitude(), vehicle.getLongitude(),asset.getLattitude(),asset.getLongitude(),"K");
                    distanceList.add(distance);

                });
                this.distanceMatrix.put(vehicle.getId(),distanceList);
            }
        });
    }


    private Map<String,List<Asset>> get1stLevelVechileMap(List<Asset> assetList){
        if(!distanceMatrix.isEmpty()){
            distanceMatrix.forEach((k,v)->{
                List<Double> distanceCoverageList=distanceMatrix.get(k);
                int minIndex = distanceCoverageList.indexOf(Collections.min(distanceCoverageList));
                List<Asset> plannedAssetList= new ArrayList <>();
                plannedAssetList.add(assetList.get(minIndex));
                this.getVechileAssetMap().put(k,plannedAssetList);
            });

        }
        return this.getVechileAssetMap();
    }

    private Map<String,List<Asset>> getVechileAssetMap(){
        if(this.vechileAssetMap.isEmpty())
            this.vechileAssetMap=new HashMap <>();

        return this.vechileAssetMap;
    }

    private Vehicle getVechileDetailsById(String id , Vechiles vechiles){
        List<Vehicle> vehicleList =vechiles.getVehicle();
        for(Vehicle vec :vehicleList){
            if(vec.getId().equalsIgnoreCase(id))
                return vec;
        }
        return null;
    }

    private  Position getLatLongFromVehicle(Vehicle vehicle){
        return Position.builder().lat(vehicle.getLattitude()).lng(vehicle.getLongitude()).build();
    }

    private  Position getLatLongFromAsset(Asset asset){
        return Position.builder().lat(asset.getLattitude()).lng(asset.getLongitude()).build();
    }


    private Map<Vehicle, Integer> getStartToFinalDestination(Vechiles vechiles, Position destination){
        Map<Vehicle,Integer> vehchileMap= new ConcurrentHashMap <>();
        List<Vehicle> vehicleList=vechiles.getVehicle();

        vehicleList.parallelStream().forEach(vehicle -> {
            log.info("Calling Routing Service for Vehicle id :- {}",vehicle.getId());
           RouteResponse response= routingService.calculateRoute(this.getLatLongFromVehicle(vehicle),destination,vechiles.getType());
            log.info("Response From Routing APi :{}",response);
           int time =this.getTime(response);
            vehchileMap.put(vehicle,time);
        });
        return vehchileMap;

    }

    private Map<Vehicle, List<AssetTimeTable>> getVehichleAssetTimeTableMap(Map<Vehicle, Integer> vehicleTimeTableMap , Assets assets){
        Map<Vehicle, List<AssetTimeTable>> assetTimeTableMap= new ConcurrentHashMap <>();

        vehicleTimeTableMap.entrySet()
                .parallelStream()
                .forEach(entry -> {
                    List<AssetTimeTable> assetTimeTables= new CopyOnWriteArrayList <>();
                    Vehicle vehicle =entry.getKey();
                    final int timeTookToReachFinalDestination=entry.getValue();
                    List<Asset> assetList=assets.getAsset();
                    assetList.parallelStream().forEach(asset -> {
                        RouteResponse response= routingService.calculateRoute(this.getLatLongFromVehicle(vehicle),
                                this.getLatLongFromAsset(asset),
                                "car");
                         int timeTookFromCurrentPostionToFirstPickUp=this.getTime(response);
                        assetTimeTables.add(AssetTimeTable.builder()
                                .asset(asset)
                                .time(timeTookToReachFinalDestination+timeTookFromCurrentPostionToFirstPickUp)
                                .build());
                    });
                    assetTimeTableMap.put(vehicle,assetTimeTables);
                });
        return assetTimeTableMap;
    }

    public Map<Vehicle, List<AssetTimeTable>> getFirstMinimumTimeTableMap(Map<Vehicle, List<AssetTimeTable>> oneStopTableMap){
        Map<Vehicle,List<AssetTimeTable>> firstPickUpSelectedMap =new HashMap <>();
        oneStopTableMap.forEach((k,v)->{
            List<AssetTimeTable> list= new ArrayList <>();
            v.sort((o1, o2) -> {
                if (o1.getTime() > o2.getTime())
                    return 1;
                else if (o1.getTime() < o2.getTime()) {
                    return -1;
                }
                return 0;
            });
            list.add(v.get(0));
            firstPickUpSelectedMap.put(k,list);
        });
        return firstPickUpSelectedMap;
    }

    public Integer getTime(RouteResponse routeResponse){
        AtomicInteger time = new AtomicInteger();
        List<Sections> sections=routeResponse.getSections();
        sections.forEach(sec->{
            List<Section> sectionList=sec.getSectionList();

            Collections.sort(sectionList, new Comparator <Section>() {
                @Override
                public int compare(Section o1, Section o2) {
                    if(o1.getTravelSummary().getDuration()>o2.getTravelSummary().getDuration())
                        return 1;
                    else if(o1.getTravelSummary().getDuration()<o2.getTravelSummary().getDuration()){
                        return -1;
                    }
                    return 0;
                }
            });
      //      sectionList.sort(Collections.reverseOrder(Comparator.comparingInt(o -> o.getTravelSummary().getDuration())));
            time.set(sectionList.get(0).getTravelSummary().getDuration());
        });
        return time.get();
    }


    private void getAssetAccomodationListByCapacity(Map<Vehicle, List<AssetTimeTable>> vehicleListMap,Map<Vehicle, List<AssetTimeTable>> oneStopTimeTableMap){
        vehicleListMap.forEach((k,v)->{
           int capacity= k.getCapacity();
           if(capacity-v.size()>0 && oneStopTimeTableMap.containsKey(k)){
               List<AssetTimeTable> secondeOderList =oneStopTimeTableMap.get(k);
               secondeOderList.sort(Comparator.comparingInt(AssetTimeTable::getTime));
               for(int i=0;i<=capacity-v.size();i++){
                     v.add(secondeOderList.get(i+1));
               }
           }
        });
    }
}

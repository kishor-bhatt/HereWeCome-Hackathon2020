
const here = {
   apiKey: 'WkLKFksiwInCkiiaJDNPOc-STvm7Ua1_FlJyyi-pwxg'
};

const colors = ['orange', '#76A8FC'];

const map = L.map('map', {
   center: [12.935280, 77.693737],
   zoom: 14,
   layers: [
      Tangram.leafletLayer({
         scene: 'scene.yaml',
      })
   ],
   zoomControl: true
});

let startCoordinates = '';
var response = {
               "vehcile": [
               {
               "id": "001",
               "number": "001",
               "routes": {
               "positionReferenceMap": [
                   {
                   "lat": 12.958108,
                   "lng": 77.671126,
                   "ref": "2"
                   },
                   {
                   "lat": 12.956946,
                   "lng": 77.701088,
                   "ref": "3"
                   },
                   {
                   "lat": 12.935280,
                   "lng": 77.693737,
                   "ref": "4"
                   }
               ]
               }
               },
               {
               "id": "002",
               "number": "002",
               "routes": {
               "positionReferenceMap": [
               {
               "lat": 12.918021,
               "lng": 77.641640,
               "ref": "2"
               },
               {
                 "lat": 12.935280,
                 "lng": 77.693737,
                 "ref": "4"
               }
               ]
               }
               }
               ]
               };


async function route(start1, end1, start2, end2) {
   const url = `https://route.ls.hereapi.com/routing/7.2/calculateroute.json
?apiKey=${here.apiKey}&waypoint0=${start1},${end1}&waypoint1=${start2},${end2}&mode=fastest;car;traffic:disabled&routeattributes=shape`
   const response = await fetch(url);
   const data = await response.json();
   return await data.response.route[0];
}


async function renderMap() {
      for(var i = 0; i< response.vehcile.length; i++) {
              for(var j = 0; j< response.vehcile[i].routes.positionReferenceMap.length-1; j++) {
                var routeData = await route(response.vehcile[i].routes.positionReferenceMap[j].lat, response.vehcile[i].routes.positionReferenceMap[j].lng,
                                        response.vehcile[i].routes.positionReferenceMap[j+1].lat, response.vehcile[i].routes.positionReferenceMap[j+1].lng);

                const shape = routeData.shape.map(x => x.split(","));
                const poly = L.polyline(shape,{ color : colors[j]}).addTo(map);
                L.marker([response.vehcile[i].routes.positionReferenceMap[j].lat, response.vehcile[i].routes.positionReferenceMap[j].lng]).addTo(map);
              }
              L.marker([response.vehcile[i].routes.positionReferenceMap[j].lat, response.vehcile[i].routes.positionReferenceMap[j].lng]).addTo(map);
         }
}

renderMap();

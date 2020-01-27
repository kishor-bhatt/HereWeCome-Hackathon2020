const here = {
   id: 'mYwoxglCiMjTXLmCGVv8',
   code: 'izpn5RnSXjuxnpOjbPXtDA'
};

const map = L.map('map', {
   center: [12.921536, 77.615553],
   zoom: 16,
   layers: [
      Tangram.leafletLayer({
         scene: 'scene.yaml',
         events: {
            click: onMapClick
         }
      })
   ],
   zoomControl: false
});

async function onMapClick() {
   //We will write code in here later...
}
# HereWeCome-Hackathon2020

Hackathon2020
BackEnd Service

Start the application using the MultiWaypointVechileSequenceApp class

Endpoint - http://localhost:8080/api/getRoute/

sample request body like below

```json
{ 
   "vehcile":[ 
      { 
         "id":"001",
         "number":"001",
         "routes":{ 
            "positionReferenceMap":{ 
               "1":{ 
                  "lat":13.0708,
                  "lng":77.6189,
                  "ref":"2"
               },
               "2":{ 
                  "lat":12.937179,
                  "lng":77.626828,
                  "ref":"3"
               },
               "3":{ 
                  "lat":13.03439,
                  "lng":13.6122,
                  "ref":"4"
               }
            }
         }
      },
      { 
         "id":"002",
         "number":"002",
         "routes":{ 
            "positionReferenceMap":{ 
               "1":{ 
                  "lat":13.3121,
                  "lng":13.6122,
                  "ref":"2"
               },
               "2":{ 
                  "lat":13.03439,
                  "lng":13.6122,
                  "ref":"3"
               }
            }
         }
      }
   ]
}

```



Sample response like below
```json
{ 
   "destination":"Here Techologies Bangalore",
   "vehicles":{ 
      "count":3,
      "type":"car",
      "vehicle":[ 
         { 
            "id":"001",
            "lattitude":"13.0408",
            "longitude":"76.61843",
            "capacity":3
         },
         { 
            "id":"002",
            "lattitude":"13.3232",
            "longitude":"75.6184",
            "capacity":2
         }
      ]
   },
   "assets":{ 
      "type":"human",
      "size":4,
      "asset":[ 
         { 
            "assetId":"0001",
            "lat":"12.937179",
            "long":"77.626828",
            "time":"28/01/2020 06:00:00"
         },
         { 
            "assetId":"0002",
            "lat":"13.0708",
            "long":"77.6189",
            "time":"28/01/2020 06:00:00"
         },
         { 
            "assetId":"0003",
            "lat":"13.03439",
            "long":"13.6122",
            "time":"28/01/2020 06:00:00"
         },
         { 
            "assetId":"0004",
            "lat":"13.3121",
            "long":"13.6122",
            "time":"28/01/2020 06:00:00"
         }
      ]
   }
}

```




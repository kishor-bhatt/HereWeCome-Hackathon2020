package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

     /*"label": "Here Technologies, Kadubeesanahalli, Bengaluru 560103, India",
             "countryCode": "IND",
             "countryName": "India",
             "state": "Karnataka",
             "county": "Bengaluru",
             "city": "Bengaluru",
             "district": "Kadubeesanahalli",
             "postalCode": "560103"*/
    @JsonProperty("label")
    private String label;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("postalCode")
    private String postalCode;
}

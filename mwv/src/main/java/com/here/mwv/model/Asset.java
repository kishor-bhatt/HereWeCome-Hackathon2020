package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
@Data
public class Asset {

    @JsonProperty("assetId")
    private String assetId;

    @JsonProperty("lat")
    private double lattitude;

    @JsonProperty("long")
    private double longitude;

   // private ZonedDateTime dateTime;
}

package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class Position {

    @JsonProperty("lat")
    private double lat;

    @JsonProperty("lng")
    private double lng;
}

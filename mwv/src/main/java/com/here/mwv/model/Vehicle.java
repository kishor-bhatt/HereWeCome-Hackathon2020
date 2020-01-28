package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Vehicle {

    @JsonProperty("id")
    private String id;

    @JsonProperty("lattitude")
    private double lattitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("capacity")
    private int capacity;

}

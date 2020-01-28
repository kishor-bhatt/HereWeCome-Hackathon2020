package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class Vechiles{

    @JsonProperty("type")
    private String type;

    @JsonProperty("count")
    private int count;

    @JsonProperty("vehicle")
    private List<Vehicle> vehicle;
}

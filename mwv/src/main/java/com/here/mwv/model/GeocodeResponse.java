package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class GeocodeResponse {
    @JsonProperty("items")
    private List<Items> items;
}

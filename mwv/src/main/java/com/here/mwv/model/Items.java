package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Items {
    @JsonProperty("title")
    private String title;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("position")
    private Position position;

}

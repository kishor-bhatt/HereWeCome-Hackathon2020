package com.here.mwv.model.route;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.here.mwv.model.Position;
import lombok.Data;

@Data
public class Place {

    @JsonProperty("type")
    private String type;

    @JsonProperty("location")
    private Position position;
}

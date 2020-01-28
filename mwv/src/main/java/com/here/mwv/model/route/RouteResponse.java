package com.here.mwv.model.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class RouteResponse {

    @JsonProperty("routes")
    private List<Sections> sections;

}

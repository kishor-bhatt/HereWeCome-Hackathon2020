package com.here.mwv.model.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TravelSummary {

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("length")
    private int length;
}

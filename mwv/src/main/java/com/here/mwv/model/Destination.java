package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
@Data
public class Destination {

    @JsonProperty("pickUpId")
    private String pickUpId;
    @JsonProperty("assets")
    private Asset assets;
    @JsonProperty("pickUpTime")
    private ZonedDateTime zonedDateTime;
}

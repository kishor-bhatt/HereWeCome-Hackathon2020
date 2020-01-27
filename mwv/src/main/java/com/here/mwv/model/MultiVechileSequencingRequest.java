package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class MultiVechileSequencingRequest {
    @JsonProperty("destination")
    @NotEmpty
    private String destination;

    @JsonProperty("vehicles")
    private List<Vechile> vechile;


    @JsonProperty("reportingTime")
    private ZonedDateTime reportingTime;





}

package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.here.mwv.model.response.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MultiWayPointSequenceResponse {

    @JsonProperty("vehcile")
    private List<Vehicle> vehicle;

}

package com.here.mwv.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
@Data
@Builder
public class Route {

    @JsonProperty
    private Map<String,PositionReference> positionReferenceMap;
}

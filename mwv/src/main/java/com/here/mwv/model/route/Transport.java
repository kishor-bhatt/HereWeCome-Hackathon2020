package com.here.mwv.model.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Transport {
    @JsonProperty("mode")
    private String mode;
}

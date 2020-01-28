package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
@Data
public class Assets {
    @JsonProperty("type")
    @NotEmpty
    private String assetType; //human

    @JsonProperty("size")
    private int size;


    @JsonProperty("asset")
    private List<Asset> asset;

}

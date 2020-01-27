package com.here.mwv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class Assets {
    @JsonProperty("assetType")
    @NotEmpty
    private String assetType; //human

    @JsonProperty("size")
    private int size;


    @JsonProperty("asset")
    private List<Asset> asset;

}

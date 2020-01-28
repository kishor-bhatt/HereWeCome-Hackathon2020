package com.here.mwv.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetTimeTable {

    private Asset asset;

    private int time;

}

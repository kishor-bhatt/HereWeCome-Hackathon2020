package com.here.mwv.model.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class Sections {

    @JsonProperty("id")
    private String id;

    @JsonProperty("sections")
    private List<Section> sectionList;
}

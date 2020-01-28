package com.here.mwv.model.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class Section {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("departure")
    private Location departure;

    @JsonProperty("arrival")
    private Location arrival;

    @JsonProperty("travelSummary")
    private TravelSummary travelSummary;

    @JsonProperty("transport")
    private Transport transport;


   /*
   private Map<String,Object> section;
   Section(){
        this.section= new LinkedHashMap <>();
    }

    @JsonAnyGetter
    public Map <String, Object> getTemplatePropertyType() {
        return section;
    }
    @JsonAnySetter
    public void setTemplatePropertyType(String key, Object value) {
        this.section.put(key,value);
    }
*/
}

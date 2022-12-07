package Lesson_4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cuisine",
        "cuisines",
        "confidence",
        "id"
})
@Data
public class ResponseCuisine {

    @JsonProperty("cuisine")
    private String cuisine;
    @JsonProperty("id")
    private String id;
    @JsonProperty("cuisines")
    private List<String> cuisines = null;
    @JsonProperty("confidence")
    private Double confidence;
    @JsonProperty("results")
    private Double results;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
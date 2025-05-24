package com.diploma.MrcX.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {

    @JsonProperty("id")
    private String id;

    @JsonProperty("username")
    private String username;
}

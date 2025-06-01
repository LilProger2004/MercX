package com.diploma.MrcX.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;



@Getter
@Setter
@Jacksonized
@Builder
public class SkillPojo {
    @JsonProperty("skill")
     String name;
}

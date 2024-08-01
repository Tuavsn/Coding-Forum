package com.hoctuan.studentcodehub.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BaseResponse {
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Status")
    private int status;
    @JsonProperty("Data")
    private Object data;
}

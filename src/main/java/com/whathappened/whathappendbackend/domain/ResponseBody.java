package com.whathappened.whathappendbackend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseBody {
    private Integer code;
    private String message;
}

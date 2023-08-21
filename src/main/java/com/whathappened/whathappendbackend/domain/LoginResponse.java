package com.whathappened.whathappendbackend.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Integer code;
    private String token;
}

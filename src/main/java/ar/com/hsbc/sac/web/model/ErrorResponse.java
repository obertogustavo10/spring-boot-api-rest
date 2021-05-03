package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String faultCode;
    private String faultString;
    private String faultActor;
    private String detail;

}

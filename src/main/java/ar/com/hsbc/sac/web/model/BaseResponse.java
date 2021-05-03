package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {

    private String apiVersion;
    private String operationId;
    private String statusCode;
    private String statusMessage;
    private String successResponse;
    private ErrorResponse errorResponse;

}

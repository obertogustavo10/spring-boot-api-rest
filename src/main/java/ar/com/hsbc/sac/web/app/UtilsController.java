package ar.com.hsbc.sac.web.app;

import ar.com.hsbc.sac.web.model.BaseResponse;
import ar.com.hsbc.sac.web.model.ErrorResponse;

public class UtilsController {
    // UTILS
    public static BaseResponse getSuccessResponse() {
        return BaseResponse.builder().apiVersion("1.0").operationId("1509090912980128785211").statusCode("200")
                .statusMessage("OK").successResponse("Ejecutado exitosamente!").build();
    }

    public static BaseResponse getNotFoundResponse() {
        ErrorResponse errorResponse = ErrorResponse.builder().faultString("SQLServerException")
                .detail("The resultset returned an empty body.").faultCode("ESSR").build();
        return BaseResponse.builder().apiVersion("1.0").operationId("1509090912980128785212").statusCode("404")
                .statusMessage("NOTFOUND").errorResponse(errorResponse).build();
    }

    public static BaseResponse getInternalErrorResponse() {
        ErrorResponse errorResponse = ErrorResponse.builder().faultString("ServerException").detail("Internal Error.")
                .faultCode("ESSR").build();
        return BaseResponse.builder().apiVersion("1.0").operationId("1509090912980128785213").statusCode("500")
                .statusMessage("INTERNALERROR").errorResponse(errorResponse).build();
    }
    
}

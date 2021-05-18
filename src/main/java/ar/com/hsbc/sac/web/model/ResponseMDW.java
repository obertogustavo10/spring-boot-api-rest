package ar.com.hsbc.sac.web.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseMDW {

    private String returnCode;
    private String returnType;
    private String returnDescription;
    private String clientNumber;
    private String docType;
    private String document;
    private List<CreditCardAdhesion> creditCardAdhesions;
    private String activityFlag;

}

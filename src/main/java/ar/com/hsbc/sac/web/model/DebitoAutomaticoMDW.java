package ar.com.hsbc.sac.web.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DebitoAutomaticoMDW {

    private String returnCode;
    private String returnDescription;
    private String clientNumber;
    private String docType;
    private String document;
    private List<CreditCardAdhesion> creditCardAdhesions;

}

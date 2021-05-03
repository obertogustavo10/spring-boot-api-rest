package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCardAdhesion {

    private String entityNumber;
    private String subentityNumber;
    private String referenceNumber;
    private String currency;
    private String branch;
    private String ctl4;
    private String accountNumber;
    private String invoiceControl;
    private String limitToControl;
    private String branchEntry;
    private String feeQuantity;
    private String adhesionFreezing;

}

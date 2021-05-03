package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CuentaDTO {

    private String operationId;
    private String requestNumber;
    private String state;
    private String documentType;
    private String documentNumber;
    private String fullName;
    private String operation;
    private String clientNumber;
    private String cuitNumber;
    private String authority;
    private String subAuthority;
    private String referenceNumber;
    private String accountNumber;
    private String dueNumber;
    private String limit;
    private String ctl4;
    private String ammount;
    private String branch;
    private String observations;
    private String userName;
    private String isBank;
    private String causeCode;
    private String reasonCode;
    private String productCode;
    private String phoneContact;
}

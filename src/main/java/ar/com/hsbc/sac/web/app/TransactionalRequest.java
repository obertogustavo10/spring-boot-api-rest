package ar.com.hsbc.sac.web.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionalRequest {

    private String operationId;
    private String documentType;
    private String documentNumber;
    private String productNumber;
    private String user;
    private String origin;
    private String option;
    private String contactMode;
    private String productCode;
    private String causeCode;
    private String reasonCode;
    private String companyCode;
    private String responsibleSector;
    private String registerSector;
    private String initContact;
    private String closeContact;

    // Relativo a Reimpresion Comun / Diferida
    private String embozo;
    private String category;

}

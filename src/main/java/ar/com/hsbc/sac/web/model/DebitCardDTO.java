package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DebitCardDTO {

    private String docType;
    private String docNumber;
    private String cardType;
    private String banelcoCardNumber;
    private String company;
    private String embozo;
    private String habilitacion;
    private String reprint;

}
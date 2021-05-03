package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Authority {

    private String registerType;
    private String authority;
    private String subAuthority;
    private String description;
    private String cuitNumber;
    private String presentation;
    private String originBank;
    private String originBankName;
    private String admitsReservation;
    private String longReferenceN;
    private String estc;

}

package ar.com.hsbc.sac.web.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteExtendidoDTO {

    private String docType;
    private String document;
    private String businessName;
    private String nationality;
    private Date birthDay;
    private String age;
    private String residencePart;
    private String cpPart;
    private String localityPart;
    private String phonePart;
    private String residenceCom;
    private String cpCom;
    private String localityCom;
    private String phoneCom;
    private String filingOffice;
    private String category;
    private String categoryCis;
    private String employee;
    private String bank;
    private String segment;
    private String mail;

}

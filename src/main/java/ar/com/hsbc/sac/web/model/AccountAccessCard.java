package ar.com.hsbc.sac.web.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountAccessCard {
    private String statCde;
    private String issPrevCde;
    private String issCde;
    private Date issInitDt;
    private Date issCurrDt;
    private BigInteger rtryPINCnt;
    private String ofstPINNum;
    private Date expirDt;
    private BigInteger plasEmbsId;
    private String replcInPrgsInd;
    private String userName;
    private String userSLName;
    private String cardType;
    private String cardSLType;
    private String catType;
    private String catSLType;
    private String cardSecndNum;
    private String expirDtText;
}

package ar.com.hsbc.sac.web.model;



import java.math.BigInteger;
import java.util.Date;

import org.apache.axis.types.Time;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardAccessArrangement {
    
    
    private String cardNum;
    private String cardPrevNum;
    private String staffInd;
    private String CIFCardInd;
    private Date tranLastDt;
    private Time tranLastTm;
    private String ATMCardInd;
    private String cardhName;
    private String typeProdCde;
    private GSMAmount cycCashWdrwVIPAmt;
    private GSMAmount cycTrsfWithnVIPAmt;
    private GSMAmount cycTrsfOutsdVIPAmt;
    private GSMAmount cycLmtPurchVIPAmt;
    private GSMAmount cycLmtCashWdrwAmt;
    private GSMAmount cycLmtTrsWithnAmt;
    private GSMAmount cycLmtTrsfBetAmt;
    private GSMAmount cycLmtPurchAmt;
    private String typeIdfcCde;
    private String idfcNum;
    private String langCde;
    private String waivrAnnlFeeInd;
    private String waivrTranChrgInd;
    private String dspcCde;
    private String collBrnchCde;
    private BigInteger addDelvrId;
    private Date loadHotDt;
    private Time loadHotTm;
    private Date mainLastDt;
    private Time mainLastTm;
    private AccountAccessCard acctAccessCard;
    private AccountArrangement primAcctInfo;
    private AccountArrangement primPurchAcctInfo;
    private AccountArrangement [] secndAcctInfo;

}

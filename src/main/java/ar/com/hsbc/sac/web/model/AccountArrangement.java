package ar.com.hsbc.sac.web.model;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AccountArrangement {
    private AccountIdentifyNumber acctIdNum;
    private String acctShrtName;
    private String acctShrtSLName;
    private String acctTypeCde;
    private String reasCloseCde;
    private String clsAllMCYInd;
    private DepositArrangement depstArr;
    private ProductArrangement prodArr;
    private String soleJointCde;
    private String classGHOCde;
    private String phoBankInd;
    private String intrntBankInd;
    private String srceIntrntFeeInd;
    private String srcePhoBankFeeInd;
    private BigInteger acctId;
    private String crncyCde;
    private String suppCbkcheckInd;
    private String tickUpChqInd;
    private String reconChqInd;
    private String rqstTranRefNumInd;
    private String ptrGHOAsetCde;
    private String ptrGHOLiabCde;
    private String ptrCentlBnkAsetCde;
    private String ptrCentlBnkLiabCde;
    private String restrCde;
    private String statCde;
    private String statSttryCde;
    private String openAcctDt;
    private String rqtIntStmtInd;
    private String waivrSvceChrgInd;
    private String methChrgCollCde;
    private String groupSvceChrgCde;
    private GSMAmount tshldBalChngAmt;
    private String atribGIMIS1Cde;
    private String atribGIMIS2Cde;
    private String autoCloseAcctInd;
    private String referCustText;
    private String LOCInd;
    private String signInd;
    private String statAcctType;
    private String psbkInd;
    private String acctName;
    private String acctMailName;
    private String balBlkInd;
    private AccountIdentifyNumber EAN;
    private String ATMLinkInd;
    private String restrDesc;
    private String statDesc;
}

package ar.com.hsbc.sac.web.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class FixedTermDepositArrangement {
    private String matcde, 
        lgthTermText,
        optTranCde,
        typeSetlCde,
        holdBalCde,
        typeDepstCde,
        freqIntCalcCde,
        intRenewInd,
        rvrsInd,
        safeCstdInd,
        refCommBaseInvNum,
        waiveBrokrInd,
        exchgContNum,
        termRenewText,
        matInstrCde,
        heldFavorDesc,
        taxFileNum,
        certDepstTermNum,
        idPymtNum;
    private Date startDt,
        matDt, 
        termRenewDueDt;
    private GSMAmount tranAmt,
        printAmt,
        intContAmt,
        intOvdueAmt,
        intPaidToDtAmt,
        penAmt,
        depstUnderLienAmt,
        intNetContAmt,
        adjPrinAmt,
        tacCollAmt,
        taxDeducAmt,
        matAmt,
        tranLclCrncyAmt;
    private AccountIdentifyNumber setlAcctNum,
        intTrnsfAcctNum,
        matTrnsfAcctNum;
    private InterestRateCondition intRateCond;
    private SettlementArrangement setlArr;
    private GSMRate penRate,
        exchgContraRate,
        fundsValueRate,
        annlyldRate,
        netYldRate;
    private TextGroup dtlInstr;
    private BigInteger renewDepstCntl;
    private BigDecimal annlYldSimpleRate;

}

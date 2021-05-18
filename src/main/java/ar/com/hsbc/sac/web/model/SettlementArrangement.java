package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettlementArrangement {
    private AccountIdentifyNumber setlAcctNum;
    private String typeSetlCde,
        crncySetlAcctCde;
    private GSMAmount intsetlAmt,
        taxIntSetlAmt,
        taxSetlAmt,
        setlLclCrcncyAmt,
        setlAcctCrncyAmt,
        taxOvdueSetlAmt;
    private ChequeInformation setChq;

}

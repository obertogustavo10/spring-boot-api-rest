package ar.com.hsbc.sac.web.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinanceServiceArrangement {
    private Date intStartDt,
        intCeaseDt;
    private AccountIdentifyNumber acctIntTrnsfNum;
    private GSMAmount intAmt,
        intLclCrncyAmt;
    private InterestRateCondition intRateCond;
    private ExchangeRateInformation exchgRateInfo;
    private CreditFacilityArragement credFacilArr;
}

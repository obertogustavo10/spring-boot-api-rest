package ar.com.hsbc.sac.web.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChequeInformation {
    private GSMAmount tranAmt,
        tranForgnCrncyAmt,
        crncyLclAmt;
    private Date availDt,
        valueDt,
        chqDepdt;
    private String chqType;
    private ExchangeRateInformation exchgRateInfo;
}

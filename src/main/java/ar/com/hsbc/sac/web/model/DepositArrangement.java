package ar.com.hsbc.sac.web.model;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositArrangement {
    private String chqDshnrType,
    reasDhnrCde,
    funcIODshnrChqCde;
    private GSMAmount chqDshnrPenAmt,
    chqActlAmt;
    private BigInteger rtrnChqCnt;
}

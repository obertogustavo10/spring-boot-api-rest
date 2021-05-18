package ar.com.hsbc.sac.web.model;
import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class GSMAmount{
    private String crncyCde;
    private BigDecimal amt;
    private BigInteger precCnt; 
    private String sgnId;
}
package ar.com.hsbc.sac.web.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterestRateCondition {
    private GSMRate intRate,
        sprdRate;
    private String typeIntRateCde,
        signSprdInd,
        sprdText;
    private BigDecimal sprdSimpleRate;
}

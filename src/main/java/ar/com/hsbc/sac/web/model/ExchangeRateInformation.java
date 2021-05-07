package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRateInformation {
    private GSMRate exchgRate,
        exchgTREATSRate;
    private String referTREATSRateNum;
}

package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditFacilityArragement {
    private ControlLimit cntlLimit,
        groupLimit;
    private GSMAmount LOCAmt;
    private String offcrCredNum;
}

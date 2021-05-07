package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ControlLimit{
    private String typeLimitCde,
        lineFacilNum;
    private GSMAmount limitAmt;
}
package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountIdentifyNumber {
    private String ctryCde,
    instcde,
    brnchNum,
    acctNum,
    acctSfxNum,
    acctFUllNum,
    acctTypeRel,
    acctStatus;
   
}

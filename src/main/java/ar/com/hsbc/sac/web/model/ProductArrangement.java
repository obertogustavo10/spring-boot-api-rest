package ar.com.hsbc.sac.web.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductArrangement {
    private FixedTermDepositArrangement fisTermDepstArr;
    private Date dueDt;
    private String referNum,
    daDpCde,
    classCBAcctCde;
    private Product prod;
    private FinanceServiceArrangement finSvceArr;
}

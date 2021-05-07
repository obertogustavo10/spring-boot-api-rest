package ar.com.hsbc.sac.web.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String typeCde,
        prodShrtName,
        prodShrtSLName,
        ctryCde,
        instCde,
        classCde,
        prodCde,
        accntState,
        CBU,
        dbtCardIndicator,
        crdtCardNmbr;
    private Date frstCloseDt,
        lstCloseDt,
        frstExprdDt,
        lstExprdDt;
}

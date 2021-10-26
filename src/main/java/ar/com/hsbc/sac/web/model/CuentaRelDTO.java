package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CuentaRelDTO {
    private String account,accountRelated,banelcoCard, detProduct, document, indivEmpres, ordConj, product, status,
        tipDoc, tipProduct, tipRelatedAccount,denominacion;
    private String [] accounts;
    private String codigo;
    private boolean resultState;
}

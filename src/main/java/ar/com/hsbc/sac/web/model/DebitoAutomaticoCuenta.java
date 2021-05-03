package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DebitoAutomaticoCuenta {

    private String tipoOperacion;
    private String tipoDocumento;
    private String numeroDocumento;
    private String numeroEntidad;
    private String numeroSubEntidad;
    private String branch;
    private String ctl14;
    private String numeroCuenta;
    private String controlFactura;
    private String controlLimite;
    private String branchEntry;
    private String numeroReferencia;
    private String moneda;

}

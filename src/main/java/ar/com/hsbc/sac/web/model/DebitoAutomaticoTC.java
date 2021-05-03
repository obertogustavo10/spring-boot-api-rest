package ar.com.hsbc.sac.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DebitoAutomaticoTC {

    private String codigoEntidad;
    private String numeroCuenta;
    private String cuentaDebito;
    private String codigoTipoCuentaBanco;
    private String codigoAdministradora;
    private String tipoDebito;
    private String sucursalDebito;
    private String sucursalDebitoUSD;
    private String codigoTipoCuentaUSD;
    private String cuentaDebitoUSD;
}
